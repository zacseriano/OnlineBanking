package com.zacseriano.onlinebanking.models.account;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.zacseriano.onlinebanking.models.user.User;

/**
 * Classe que implementa o model/entidade que representa a Conta
 */
@Entity
public class Account {
	
	@NotNull @Id
	private String number;
	@NotNull 
	private BigDecimal balance;
	@ManyToOne
	private User user;
	
	public Account() {
		
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, number, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(balance, other.balance) && Objects.equals(number, other.number)
				&& Objects.equals(user, other.user);
	}
	
}
