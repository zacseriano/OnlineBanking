package com.zacseriano.onlinebanking.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção que dispara quando o token JWT informado é inválido.
 *  
 */ 
@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Acesso Negado.")
public class InvalidTokenException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public InvalidTokenException() {
		super("Acesso Negado");
	}

	public InvalidTokenException(String msg, Throwable t) {
		super(msg, t);
	}
}