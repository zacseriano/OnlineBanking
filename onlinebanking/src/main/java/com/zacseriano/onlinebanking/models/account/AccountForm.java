package com.zacseriano.onlinebanking.models.account;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.zacseriano.onlinebanking.exceptions.account.ExistingAccountException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.SmallAccountNumberException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
import com.zacseriano.onlinebanking.repositories.UserRepository;
/*
 * Classe do formulário que será usado no método createAccount(), responsável
 * por criar uma conta.
 */
public class AccountForm {
	
	@NotNull
	private String number;
	
	@NotNull
	private BigDecimal balance;	
	
	public AccountForm(String number, BigDecimal hundred) {
		this.number = number;
		this.balance = hundred;
	}

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

	public Account converter(UserRepository userRepository, AccountRepository accountRepository) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String email = userDetails.getUsername();
		User user = userRepository.findByEmail(email);
		
		Account account = accountRepository.findByNumber(this.number);
		if(account != null) throw new ExistingAccountException();
		if(this.getNumber() == null ||
				this.getNumber().length()< 6) throw new SmallAccountNumberException();
		
		BigDecimal zero = new BigDecimal("0");
		if(this.balance.compareTo(zero) == -1) throw new NegativeBalanceException();	
				
		return new Account(this.number, this.balance, user);
	}
}
