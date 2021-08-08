package com.zacseriano.onlinebanking.models.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
/*
 * Classe utilizada no ambiente de testes para simular a geração de um token JWT
 */
public class UserTestFactory {

	public static UserTest create(User user) {
		return new UserTest(user.getEmail(), user.getPassword(), createGrantedAuthorities("ROLE_USER"));
	}
	
	private static List<GrantedAuthority> createGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

}