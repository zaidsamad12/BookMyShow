package com.example.bookmyshow.services;

import com.example.bookmyshow.models.User;
import com.example.bookmyshow.respositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOrCreateUser(Long userId, String email, String password){
        User user = login(userId, email, password);
        if(user == null){
            user = signUp(userId, email, password);
        }
        return user;
    }

    private User login(Long userId, String email, String password) {
        User user = null;
        Optional<User> optionalUser = null;
        optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }
        if(user == null){
            optionalUser = userRepository.findByEmail(email);
            if(optionalUser.isPresent()){
                user = optionalUser.get();
            }
            else{
                throw new RuntimeException("Invalid userId or Email");
            }
        }

        BCryptPasswordEncoder bCryptPasswordEncoder1 = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder1.matches(user.getPassword(), password)){
            return user;
        }
        throw new RuntimeException("Password is incorrect");
    }

    public User signUp(Long userId, String email, String password){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUserid(userId);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setTickets(new ArrayList<>());
        return user;
    }

}
