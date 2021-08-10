package com.zacseriano.onlinebanking.security.form;

import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.zacseriano.onlinebanking.repositories.UserRepository;

/*
 * Classe que é implementada com dois campos, passando email e password como informações para o formulário usado
 * nos seguintes métodos dos endpoints:
 * 		- createAuthenticationToken() no /auth
 * 		- registerUser() no /users	
 */
public class AuthForm {	
	
	@NotNull(message="O email não pode estar vazio.")
	private String email;
	
	@NotNull(message="A password não pode estar vazia.")
	private String password;
	
	public AuthForm(@NotNull(message = "O email não pode estar vazio.") String email,
			@NotNull(message = "A password não pode estar vazia.") String password) {
		this.email = email;
		this.password = password;
	}
	
	public AuthForm() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean verifyUser(UserRepository ur) {
		if(ur.findByEmail(this.email) == null)
			return false;
		return true;
	}
	
	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
