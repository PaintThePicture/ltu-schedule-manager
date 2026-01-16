package com.github.models.entities;

import java.util.Arrays;

public class CanvasAuthorization {
    private final char[] token = new char[256];
    private final String userId;

    public CanvasAuthorization (String userId, String token) {
        this.userId = userId;
         
        if (token == null) return;

        Arrays.fill(this.token, '\0');

        int length = Math.min(this.token.length, token.length());
        for (int i = 0; i < length; i++) {
            this.token[i] = token.charAt(i);
        }
    }

    public char[] getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public void wipe() {
        if (this.token != null) {
            Arrays.fill(token, '\0');
        }
    }

    public String getExposedToken() {
        return (token != null && token.length > 0) ? new String(this.token).trim() : "";
    }

    public char[] getTokenCopy() {
        return (token != null) ? token.clone() : new char[0];
    }
}
