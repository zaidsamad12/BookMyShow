package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.BookTicketRequestDto;
import com.example.bookmyshow.dtos.BookTicketResponseDto;
import com.example.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.models.Ticket;
import com.example.bookmyshow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {

        this.ticketService = ticketService;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto bookTicketRequestDto) throws ShowSeatNotAvailableException {
        BookTicketResponseDto bookTicketResponseDto = new BookTicketResponseDto();
        try {
            Ticket ticket = ticketService.bookTicket(bookTicketRequestDto.getShowSeats(), bookTicketRequestDto.getUserId()
                    , bookTicketRequestDto.getShowId());

            bookTicketResponseDto.setTicket(ticket);
            bookTicketResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(RuntimeException runtimeException){
            bookTicketResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return bookTicketResponseDto;
    }
}
