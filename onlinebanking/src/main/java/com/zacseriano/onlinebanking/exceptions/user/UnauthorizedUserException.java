package com.zacseriano.onlinebanking.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Conta não pertence ao usuário informado.") 
public class UnauthorizedUserException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public UnauthorizedUserException() {
		super("Conta não pertence ao usuário informado.");
	}

	public UnauthorizedUserException(String msg, Throwable t) {
		super(msg, t);
	}
}
