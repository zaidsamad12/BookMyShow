package com.example.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseModel{

    private String name;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    List<Feature> movieFeatures;

    @ManyToMany
    List<Actor> actors;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    List<Genre> genres;
}
