package com.quizserver.services;

import com.quizserver.exceptions.InvalidJwtException;
import com.quizserver.models.DTOs.auth.SignUpDto;
import com.quizserver.models.entities.User;
import com.quizserver.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByLogin(username);
    }

    public UserDetails signUp(SignUpDto data) throws InvalidJwtException {

        if (repository.findByLogin(data.login()) != null) {
            throw new InvalidJwtException("Username already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.login(), encryptedPassword, data.role());

        return repository.save(newUser);

    }
}
