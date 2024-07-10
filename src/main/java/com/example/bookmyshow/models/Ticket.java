package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket extends BaseModel{

    @ManyToOne
    private User BookedBy;

    @OneToMany(mappedBy = "ticket")
    private List<Payment> payments;

    @ManyToMany // because we are supporting cancellation and refund so one show seat can be a part of multiple tickets
    private List<ShowSeat> showSeats;

    @ManyToOne
    private Show show;

    private double totalAmount;

    private Date timeOfBooking;

    @Enumerated(EnumType.ORDINAL)
    private TicketStatus ticketStatus;

}
