package com.example.bookmyshow.services;

import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.ShowSeat;
import com.example.bookmyshow.models.ShowSeatType;
import com.example.bookmyshow.respositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculationService {

    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PriceCalculationService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public double calculatePrice(List<ShowSeat> showSeatsList, Show show){
        int amount = 0;
        List<ShowSeatType> showSeatTypeList = showSeatTypeRepository.findAllByShowId(show.getId());

        for(ShowSeatType showSeatType : showSeatTypeList){
            for(ShowSeat showSeat : showSeatsList){
                if(showSeatType.getSeatType().equals(showSeat.getSeat().getSeatType())){
                    amount+=showSeatType.getPrice();
                }
            }
        }
        return amount;
    }
}
