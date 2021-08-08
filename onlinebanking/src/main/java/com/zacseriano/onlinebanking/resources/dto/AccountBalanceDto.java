package com.zacseriano.onlinebanking.resources.dto;

import java.math.BigDecimal;

import com.zacseriano.onlinebanking.models.account.Account;
/*
 * Classe de DTO usado para representar uma conta no método showTransfer() do AccountResource
 * na consulta de saldo
 */
public class AccountBalanceDto {
	
	private String number;
	private BigDecimal balance;
	
	public AccountBalanceDto(Account account) {
		this.number = account.getNumber();
		this.balance = account.getBalance();
	}

	public String getNumber() {
		return number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

}
