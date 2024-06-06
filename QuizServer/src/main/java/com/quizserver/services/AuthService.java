package com.quizserver.services;

import com.quizserver.models.DTOs.auth.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final LoginUser adminAccount = new LoginUser("admin", "admin");
    private final JWTService JWTService;

    @Autowired
    public AuthService(JWTService JWTService) {
        this.JWTService = JWTService;
    }

    public String login(LoginUser loginUser) {
        if(!loginUser.getUsername().equals(adminAccount.getUsername()) ||
                !loginUser.getPassword().equals(adminAccount.getPassword())) return null;

        return JWTService.generateToken(loginUser);
    }

    public boolean isAdmin(String token) {
        try{
            return JWTService.isTokenValid(token, adminAccount);
        } catch (Exception e) {
            return false;
        }

    }
}
