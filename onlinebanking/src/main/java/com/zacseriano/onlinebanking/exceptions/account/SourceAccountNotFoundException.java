package com.zacseriano.onlinebanking.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/*
 * Classe que implementa a exceção que é disparada quando o número de conta 
 * de origem informado não encontra resultados.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Conta de origem inexistente com o número informado.") 
public class SourceAccountNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public SourceAccountNotFoundException() {
		super("Conta de origem inexistente com o número informado.");
	}

	public SourceAccountNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}