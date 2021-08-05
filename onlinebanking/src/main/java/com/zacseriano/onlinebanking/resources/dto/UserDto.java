package com.zacseriano.onlinebanking.resources.dto;

import com.zacseriano.onlinebanking.models.user.User;

public class UserDto {
	
	public UserDto(User user) {	
		this.email = user.getEmail();
		this.name = user.getName();
	}	
	
	private String email;
	private String name;
	
	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}
}
