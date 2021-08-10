package com.zacseriano.onlinebanking.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Classe que implementa a exceção que é disparada quando o número de conta 
 * de origem informado não encontra resultados.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Escolha um número de conta que contenha mais de 6 caracteres.") 
public class SmallAccountNumberException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public SmallAccountNumberException() {
		super("Escolha um número de conta que contenha mais de 6 caracteres.");
	}

	public SmallAccountNumberException(String msg, Throwable t) {
		super(msg, t);
	}
}