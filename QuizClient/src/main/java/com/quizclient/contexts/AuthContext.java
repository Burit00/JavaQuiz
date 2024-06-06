package com.quizclient.contexts;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AuthContext {
    private static String token = null;
    private static final BooleanProperty isLogged = new SimpleBooleanProperty(false);

    public static String getToken() {
        return token;
    }

    public static void setToken(String  token) {
        AuthContext.token = token;
        isLogged.setValue(true);
    }

    public static void removeToken() {
        AuthContext.token = null;
        isLogged.setValue(false);
    }

    public static BooleanProperty getIsLogged() {
        return isLogged;
    }
}
