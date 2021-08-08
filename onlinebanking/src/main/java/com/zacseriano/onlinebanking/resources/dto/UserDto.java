package com.zacseriano.onlinebanking.resources.dto;

import com.zacseriano.onlinebanking.models.user.User;
/*
 * Classe de DTO usado para representar uma usuário no registerUser() do UserResource
 * na consulta de saldo.
 */
public class UserDto {
		
	private String email;
	private String name;
		
	public UserDto(User user) {	
		this.email = user.getEmail();
		this.name = user.getName();
	}
	
	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}
}
