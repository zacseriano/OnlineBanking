package com.zacseriano.onlinebanking.models.account;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.zacseriano.onlinebanking.exceptions.account.AccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.user.UnauthorizedUserException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
import com.zacseriano.onlinebanking.repositories.UserRepository;

public class AccountBalanceForm {
	
	@NotNull
	private String number;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public Account converter(UserRepository userRepository, AccountRepository accountRepository) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String email = userDetails.getUsername();
		User user = userRepository.findByEmail(email);
		
		Account account = accountRepository.findByNumber(this.number);
		if(account == null) throw new AccountNotFoundException();
		
		if(user.getEmail() != account.getUser().getEmail())
			throw new UnauthorizedUserException();
		
		return account;		
	}
}
