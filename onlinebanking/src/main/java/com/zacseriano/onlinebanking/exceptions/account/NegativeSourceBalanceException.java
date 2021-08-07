package com.zacseriano.onlinebanking.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="O saldo da conta de origem não pode se tornar negativo.")  // 404
public class NegativeSourceBalanceException extends RuntimeException {
	private static final long serialVersionUID = 6648725043534411041L;

	public NegativeSourceBalanceException() {
		super("O saldo da conta de origem não pode se tornar negativo.");
	}

	public NegativeSourceBalanceException(String msg, Throwable t) {
		super(msg, t);
	}
}
