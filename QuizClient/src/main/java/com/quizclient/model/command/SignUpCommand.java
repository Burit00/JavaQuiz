package com.quizclient.model.command;

import com.quizclient.enums.UserRoleEnum;

public class SignUpCommand {
    private String login;
    private String password;

    private String confirmPassword;
    private UserRoleEnum role;

    public SignUpCommand() {}

    public SignUpCommand(String login, String password, String confirmPassword, UserRoleEnum userRole) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = userRole;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum userRole) {
        this.role = userRole;
    }
}
