package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.SignupRequestDto;
import com.example.bookmyshow.dtos.SignupResponseDto;
import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.respositories.UserRepository;
import com.example.bookmyshow.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Getter
@Setter
@Controller
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {

        this.userRepository = userRepository;
        this.userService = userService;
    }

    public SignupResponseDto signUp(SignupRequestDto signupRequestDto){

        User user =  null;
        SignupResponseDto signupResponseDto = new SignupResponseDto();
        try {
            user = userService.findOrCreateUser(signupRequestDto.getUserId(),
                    signupRequestDto.getEmail(), signupRequestDto.getPassword());
            signupResponseDto.setUserId(user.getUserid());
            signupResponseDto.setEmailId(user.getEmail());
            signupResponseDto.setPassword(user.getPassword());
            signupResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            signupResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            throw new UserNotFoundException(user.getUserid());
        }
        return signupResponseDto;
    }
}
