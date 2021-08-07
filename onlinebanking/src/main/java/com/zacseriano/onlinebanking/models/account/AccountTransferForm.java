package com.zacseriano.onlinebanking.models.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AccountTransferForm {
	
	@NotNull
	private String number;
	
	@Email
	private String userEmail;


}
