package com.zacseriano.onlinebanking.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Conta de destino inexistente com o número informado.") 
public class DestinationAccountNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public DestinationAccountNotFoundException() {
		super("Conta de destino inexistente com o número informado.");
	}

	public DestinationAccountNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}