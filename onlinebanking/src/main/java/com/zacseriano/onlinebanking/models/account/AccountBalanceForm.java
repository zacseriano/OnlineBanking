package com.zacseriano.onlinebanking.models.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.zacseriano.onlinebanking.exceptions.account.AccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.user.UnauthorizedUserException;
import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
import com.zacseriano.onlinebanking.repositories.UserRepository;

public class AccountBalanceForm {
	
	@NotNull
	private String number;
	
	@Email
	private String userEmail;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
		if(account == null) throw new AccountNotFoundException();
		
		if(user.getEmail() != account.getUser().getEmail())
			throw new UnauthorizedUserException();
		
		return account;		
	}
}
