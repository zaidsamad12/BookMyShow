package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.respositories.ShowRepository;
import com.example.bookmyshow.respositories.ShowSeatRepository;
import com.example.bookmyshow.respositories.TicketRepository;
import com.example.bookmyshow.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TicketService {

    private ShowSeatRepository showSeatRepository;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private PriceCalculationService priceCalculationService;

    @Autowired
    public TicketService(ShowSeatRepository showSeatRepository,
                         TicketRepository ticketRepository,
                         UserRepository userRepository,
                         ShowRepository showRepository,
                         PriceCalculationService priceCalculationService) {
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository=userRepository;
        this.showRepository = showRepository;
        this.priceCalculationService = priceCalculationService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(List<Long> showSeatsId, Long userId, Long showId) throws ShowSeatNotAvailableException {
        /*
             (Approach#1)
            -------------TAKE A LOCK----------------
            1. Get the user from userId.
            2. Get the show from showId.
            3. Get the list of showSeats from showSeatIds.
            4. Check if all the show seats are available.
            5. If all the show seats are not available then throw an exception.
            6. If all are available, then change the status to be LOCKED.
            7. Change the status in DB as well.
            8. Create the Ticket Object, and store it in DB.
            9. Return the Ticket Object.
            -----------RELEASE THE LOCK---------------
         */

        // 1. Get the user from userId.
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException(userId);
        }
        User bookedByUser = optionalUser.get();

        // 2. Get the show from showId.
        Optional<Show> optionaShow = showRepository.findById(showId);
        if(optionaShow.isEmpty()){
            throw new UserNotFoundException(showId);
        }
        Show show = optionaShow.get();

        // 3. Get the list of showSeats from showSeatIds.
        // 4. Check if all the show seats are available.
        // 5. If all the show seats are not available then throw an exception.
        List<ShowSeat> showSeats = showSeatRepository.findAllByIdIn(showSeatsId);
        for(ShowSeat showSeat : showSeats){
            if(!showSeat.getShowSeatState().equals(ShowSeatState.AVAILABLE)){
                throw new ShowSeatNotAvailableException(showSeat.getId());
            }
        }

        // 6. If all are available, then change the status to be LOCKED.
        // 7. Change the status in DB as well.
        List<ShowSeat> showSeatsList = new ArrayList<>();
        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatState(ShowSeatState.LOCKED);
            showSeatsList.add(showSeatRepository.save(showSeat));
        }

        // 8. Create the Ticket Object, and store it in DB.
        Ticket ticket = new Ticket();
        ticket.setBookedBy(bookedByUser);
        ticket.setShow(show);
        ticket.setShowSeats(showSeatsList);
        ticket.setTimeOfBooking(new Date());
        ticket.setTicketStatus(TicketStatus.PENDING);
        ticket.setTotalAmount(priceCalculationService.calculatePrice(showSeatsList, show));

        // 9. Return the Ticket Object.
        return ticketRepository.save(ticket);

        //---- Lock will be released automatically as the function ends---//
    }
}
