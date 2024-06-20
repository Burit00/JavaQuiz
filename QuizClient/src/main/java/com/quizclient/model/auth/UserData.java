package com.quizclient.model.auth;

import com.quizclient.enums.UserRoleEnum;

public class UserData {
    private String username;
    private UserRoleEnum role;

    public String getUsername() {
        return username;
    }

    public UserRoleEnum getRole() {
        return role;
    }
}
