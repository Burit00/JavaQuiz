package com.quizclient.enums;

public enum UserRoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;
    public String getValue() {
        return role;
    }

    UserRoleEnum(String role) {
        this.role = role;
    }
}