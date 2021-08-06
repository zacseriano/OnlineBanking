package com.zacseriano.onlinebanking.exceptions.user;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Classe que implementa a ExistingUserException na API, acionado quando se tenta
 *  criar um Usuário com o email solicitado já existe
 *  
 */ 
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Usuário já cadastrado.")  // 404
public class ExistingUserException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public ExistingUserException() {
		super("Usuário com estas credenciais já cadastrado, favor contactar o suporte para recuperar a senha.");
	}

	public ExistingUserException(String msg, Throwable t) {
		super(msg, t);
	}
}