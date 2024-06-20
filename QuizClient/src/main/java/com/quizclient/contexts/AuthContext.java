package com.quizclient.contexts;

import com.quizclient.model.auth.UserData;
import com.quizclient.helpers.JwtDecoder;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class AuthContext {
    private static String token = null;
    private static final Property<UserData> userDataProperty = new SimpleObjectProperty<>(null);

    public static String getToken() {
        return token;
    }

    public static void setToken(String  token) {
        AuthContext.token = token;
        UserData userData = token != null ? JwtDecoder.decode(token) : null;

        userDataProperty.setValue(userData);
    }

    public static void removeToken() {
        AuthContext.token = null;
        userDataProperty.setValue(null);
    }

    public static Property<UserData> getUserData() {
        return userDataProperty;
    }
}
