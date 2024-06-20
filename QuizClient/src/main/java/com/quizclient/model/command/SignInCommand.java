package com.quizclient.model.command;

public class SignInCommand {
    private String login;
    private String password;

    public SignInCommand() {}

    public SignInCommand(String login, String password) {
        this.login = login;
        this.password = password;
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
}
