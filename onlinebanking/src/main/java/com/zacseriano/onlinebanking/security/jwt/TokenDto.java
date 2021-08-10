package com.zacseriano.onlinebanking.security.jwt;

/*
 * Classe DTO usada na geração de um token JWT
 */
public class TokenDto {

	private String name;
	private String email;
	private String token;

	public TokenDto(String name, String email, String token) {
		this.name = name;
		this.email = email;
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
}
