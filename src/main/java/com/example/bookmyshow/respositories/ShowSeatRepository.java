package com.example.bookmyshow.respositories;

import com.example.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findAllByIdIn(List<Long> showSeatIds);

    @Override
    ShowSeat save( ShowSeat entity);//Upsert operation
}
