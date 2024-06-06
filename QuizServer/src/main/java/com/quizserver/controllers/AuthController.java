package com.quizserver.controllers;

import com.quizserver.models.DTOs.auth.LoginUser;
import com.quizserver.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser) {
        String token = authService.login(loginUser);
        if(token == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
