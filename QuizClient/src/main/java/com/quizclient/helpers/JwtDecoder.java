package com.quizclient.helpers;

import com.google.gson.Gson;
import com.quizclient.model.auth.UserData;

import java.util.Base64;

public class JwtDecoder {
    private static final Gson gson = new Gson();

    public static UserData decode(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        return gson.fromJson(payload, UserData.class);
    }
}
