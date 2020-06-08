package com.svulinovic.cityapi.service;

import com.svulinovic.cityapi.exception.BadRequestException;
import com.svulinovic.cityapi.exception.ExceptionConstants;
import com.svulinovic.cityapi.exception.UnauthorizedRequestException;
import com.svulinovic.cityapi.model.User;
import com.svulinovic.cityapi.model.request.CreateUserRequest;
import com.svulinovic.cityapi.model.request.LoginRequest;
import com.svulinovic.cityapi.model.response.LoginResponse;
import com.svulinovic.cityapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackFor = Exception.class)
    public LoginResponse create(CreateUserRequest request) {

        //check if user with provided email already exists
        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            throw new BadRequestException(ExceptionConstants.USER_EXISTS);
        }

        //encrypt password and generate token before saving user to database
        user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setToken(UUID.randomUUID()); //bilo bi dobro izgenerirati novi token svaki put kada se korisnik ulogira

        user = userRepository.save(user);

        //create response object
        LoginResponse response = new LoginResponse();
        response.setToken(user.getToken().toString());
        return response;
    }

    public LoginResponse login(LoginRequest request) {

        //check if user exists in database
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new BadRequestException(ExceptionConstants.USER_NOT_FOUND);
        }

        //check if password matches
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedRequestException(ExceptionConstants.INVALID_PASSWORD);
        }

        //create response object
        LoginResponse response = new LoginResponse();
        response.setToken(user.getToken().toString());
        return response;
    }

    public User findByToken(String token) { //trebalo bi srediti authorizaciju preko spring security-a, kada bude vremena
        UUID uuid;
        try {
            uuid = UUID.fromString(token);
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedRequestException(ExceptionConstants.INVALID_TOKEN);
        }

        User user = userRepository.findByToken(uuid);
        if (user == null) {
            throw new UnauthorizedRequestException(ExceptionConstants.INVALID_TOKEN);
        }
        return user;
    }

}
