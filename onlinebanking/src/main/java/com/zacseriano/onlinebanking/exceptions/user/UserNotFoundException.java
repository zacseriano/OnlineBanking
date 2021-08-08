package com.zacseriano.onlinebanking.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
 * Exceção que é disparada quando um usuário com as credenciais informadas
 * não é encontrado
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Conta inexistente com os dados informados.") 
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public UserNotFoundException() {
		super("Conta inexistente com os dados informados.");
	}

	public UserNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}