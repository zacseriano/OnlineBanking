package com.zacseriano.onlinebanking.resources.dto;

import java.math.BigDecimal;

import com.zacseriano.onlinebanking.models.account.AccountTransferForm;
import com.zacseriano.onlinebanking.repositories.UserRepository;

public class AccountTransferDto {

	private BigDecimal amount;
	private String source_account_number;
	private String destination_account_number;
	private UserDto user_transfer;
	
	public AccountTransferDto(AccountTransferForm form, UserRepository repository) {
		
		this.amount = form.getAmount();
		this.source_account_number = form.getSource_account_number();
		this.destination_account_number = form.getDestination_account_number();
		this.user_transfer = new UserDto(repository.findByEmail(form.getUserEmail()));
	}

	public String getSource_account_number() {
		return source_account_number;
	}

	public String getDestination_account_number() {
		return destination_account_number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public UserDto getUser_transfer() {
		return user_transfer;
	}

}
