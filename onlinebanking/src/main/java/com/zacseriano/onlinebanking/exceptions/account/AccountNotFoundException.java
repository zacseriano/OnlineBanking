package com.zacseriano.onlinebanking.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Conta inexistente com este número informado.")  // 404
public class AccountNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public AccountNotFoundException() {
		super("Conta inexistente com este número informado.");
	}

	public AccountNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}