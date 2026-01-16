/*
 * ltu-schedule-manager: Ett verktyg för att hantera och exportera scheman 
 * från TimeEdit till Canvas för studenter vid Luleå tekniska universitet.
 *
 * Copyright (C) 2025  Alexander Edemalm, 
 * Ronak Olyaee, Therese Henriksson, Jakob Nilsson
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA 02110-1301 USA.
 */
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
