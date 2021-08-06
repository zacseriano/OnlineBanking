package com.zacseriano.onlinebanking.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a ExistingAccountException na API, acionado quando uma conta com
 * o número solicitado já existe
 */ 
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Conta com este número já existente.")  // 404
public class ExistingAccountException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public ExistingAccountException() {
		super("Conta com este número já existente.");
	}

	public ExistingAccountException(String msg, Throwable t) {
		super(msg, t);
	}
}
