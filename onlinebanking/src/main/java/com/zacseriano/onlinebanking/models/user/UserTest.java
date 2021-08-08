package com.zacseriano.onlinebanking.models.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
/*
 * Classe utilizada no ambiente de testes para simular a geração de um token JWT
 */
public class UserTest implements UserDetails {

	private static final long serialVersionUID = -8328911063439191378L;

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserTest(String username, String password, Collection<? extends GrantedAuthority> authorities) {	
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public UserTest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}