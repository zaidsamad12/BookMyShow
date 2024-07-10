package com.example.bookmyshow.dtos;

import com.example.bookmyshow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {

    private Long userId;
    private String emailId;
    private String password;
    private ResponseStatus responseStatus;
}
