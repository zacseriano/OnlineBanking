package com.zacseriano.onlinebanking.security;

import java.io.Serializable;
/*
 * Classe usada na geração de JWT
 */
@SuppressWarnings("serial")
public class AuthenticationResponse implements Serializable {

	private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
