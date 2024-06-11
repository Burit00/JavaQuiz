package com.quizserver.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;
    public String getValue() {
        return role;
    }

    UserRole(String role) {
        this.role = role;
    }
}