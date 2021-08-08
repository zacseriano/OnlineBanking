package com.zacseriano.onlinebanking.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
 * Classe que implementa a UnauthorizedUserException na API, acionado quando o
 * usuário logado tenta fazer uma operação em uma conta não registrada
 * nas suas credenciais.  
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Conta de origem não pertence ao usuário logado.") 
public class UnauthorizedUserException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public UnauthorizedUserException() {
		super("Conta de origem não pertence ao usuário logado.");
	}

	public UnauthorizedUserException(String msg, Throwable t) {
		super(msg, t);
	}
}
