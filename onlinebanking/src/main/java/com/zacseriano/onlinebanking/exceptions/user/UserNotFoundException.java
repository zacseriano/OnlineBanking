package com.zacseriano.onlinebanking.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a UserNotFoundException na API
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Usuário inexistente.")  // 404
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public UserNotFoundException() {
		super("Usuário inexistente.");
	}

	public UserNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}