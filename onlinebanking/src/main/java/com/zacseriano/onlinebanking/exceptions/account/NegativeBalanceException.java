package com.zacseriano.onlinebanking.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe que implementa a NegativeBalanceException na API, acionado quando o saldo
 * da conta será negativo por alguma transação ou criação
 */ 
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="O saldo da conta não pode ser negativo.")  // 404
public class NegativeBalanceException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public NegativeBalanceException() {
		super("O saldo da conta não pode ser negativo.");
	}

	public NegativeBalanceException(String msg, Throwable t) {
		super(msg, t);
	}
}
