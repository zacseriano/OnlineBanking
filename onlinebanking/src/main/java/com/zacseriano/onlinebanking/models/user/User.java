package com.zacseriano.onlinebanking.models.user;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zacseriano.onlinebanking.models.account.Account;

/**
 * Classe que implementa o model/entidade que representa o Usuário
 */
@Entity
@Table(name="bank_user")
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id @Email
	private String email;
	
	@NotBlank @Size(min = 6)
	private String password;
	
	@NotBlank @Size(min = 4, max = 20)
	private String name;
	
	@OneToMany(mappedBy = "user")
	private List<Account> account;
	
	public User() {
		
	}
	
	public User(@Email String email, @NotBlank @Size(min = 6) String password,
			@NotBlank @Size(min = 4, max = 20) String name) {
		this.email = email;
		this.password = password;
		this.name = name;
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
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Account> getAccount() {
		return account;
	}

	public void setAccount(List<Account> account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account, email, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(account, other.account) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
	            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	 
	        return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
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
