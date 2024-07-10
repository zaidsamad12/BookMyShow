package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseModel{

    Long userid;
    String email;
    String userName;
    String password;
    @OneToMany
    List<Ticket> tickets;
}
