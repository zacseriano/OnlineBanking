package com.zacseriano.onlinebanking.models.account;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.zacseriano.onlinebanking.exceptions.account.DestinationAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeSourceBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.SourceAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.user.UnauthorizedUserException;
import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
import com.zacseriano.onlinebanking.repositories.UserRepository;

public class AccountTransferForm {
	
	@NotNull
	private String source_account_number;
	
	@NotNull
	private String destination_account_number;
	
	@NotNull 
	private BigDecimal amount;
	
	@Email
	private String userEmail;
			
	public String getSource_account_number() {
		return source_account_number;
	}

	public void setSource_account_number(String source_account_number) {
		this.source_account_number = source_account_number;
	}

	public String getDestination_account_number() {
		return destination_account_number;
	}

	public void setDestination_account_number(String destination_account_number) {
		this.destination_account_number = destination_account_number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Account convertSource(UserRepository userRepository, AccountRepository accountRepository) {
		
		User user = userRepository.findByEmail(this.userEmail);
		if(user == null) throw new UserNotFoundException();
		
		Account sourceAccount = accountRepository.findByNumber(this.source_account_number);
		if(sourceAccount == null) throw new SourceAccountNotFoundException();
		
		if(user.getEmail() != sourceAccount.getUser().getEmail())
			throw new UnauthorizedUserException();
		
		BigDecimal zero = new BigDecimal("0");
		BigDecimal subTest = sourceAccount.getBalance().subtract(this.amount);
		if(subTest.compareTo(zero) == -1) 
			throw new NegativeSourceBalanceException();
		
		sourceAccount.setBalance(sourceAccount.getBalance().subtract(this.amount));		
		
		return sourceAccount;		
	}
	
	public Account convertDestination(UserRepository userRepository, AccountRepository accountRepository) {
		
		Account destinationAccount = accountRepository.findByNumber(this.destination_account_number);
		if(destinationAccount == null) throw new DestinationAccountNotFoundException();
		
		destinationAccount.setBalance(destinationAccount.getBalance().add(this.amount));
		
		return destinationAccount;		
	}

}
