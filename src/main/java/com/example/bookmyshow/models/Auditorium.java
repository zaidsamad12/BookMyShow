package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
public class Auditorium extends BaseModel{

    private String  name;
    @ManyToOne
    private Theatre theatre;
    @OneToMany
    private List<Seat> seats;
    @OneToMany
    private List<Show> shows;
    @ElementCollection
    @Enumerated(EnumType.ORDINAL) /* This is for how enums will be stored in db
    EnumType.ORDINAL means how enums will be stored in db like 1,2 depending on how they are defined in enum class
     based on the order*/
    private List<Feature> auditoriumFeatures;
}
