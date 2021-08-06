package com.zacseriano.onlinebanking.resources.dto;

import java.math.BigDecimal;

import com.zacseriano.onlinebanking.models.account.Account;

public class AccountDto {
	
	private String number;
	private BigDecimal balance;
	private UserDto user;
	
	public AccountDto(Account account) {
		this.number = account.getNumber();
		this.balance = account.getBalance();
		this.user = new UserDto(account.getUser());
	}

	public String getNumber() {
		return number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public UserDto getUser() {
		return user;
	}
	
}
