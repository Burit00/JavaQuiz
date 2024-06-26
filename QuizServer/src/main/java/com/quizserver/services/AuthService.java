package com.quizserver.services;

import com.quizserver.exceptions.InvalidJwtException;
import com.quizserver.models.DTOs.auth.SignUpDto;
import com.quizserver.models.entities.User;
import com.quizserver.repositories.IUserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByLogin(username);
    }

    public UserDetails signUp(SignUpDto data) throws InvalidJwtException, BadRequestException {

        if (repository.findByLogin(data.login()) != null)
            throw new InvalidJwtException("Username already exists");

        if (data.role() == null)
            throw new BadRequestException("User must have role");

        User newUser = new User(data.login(), data.password(), data.role());

        return repository.save(newUser);

    }
}
