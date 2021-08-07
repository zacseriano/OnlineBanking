package com.zacseriano.onlinebanking.models.account;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.zacseriano.onlinebanking.exceptions.account.ExistingAccountException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeBalanceException;
import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
import com.zacseriano.onlinebanking.repositories.UserRepository;

public class AccountForm {
	
	@NotNull
	private String number;
	
	@NotNull
	private BigDecimal balance;
	
	@Email
	private String userEmail;
	
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Account converter(UserRepository userRepository, AccountRepository accountRepository) {
		
		User user = userRepository.findByEmail(userEmail);
		if(user == null) throw new UserNotFoundException();
		
		Account account = accountRepository.findByNumber(this.number);
		if(account != null) throw new ExistingAccountException();
		
		BigDecimal zero = new BigDecimal("0");
		if(this.balance.compareTo(zero) == -1) throw new NegativeBalanceException();	
				
		return new Account(this.number, this.balance, user);
	}
}
