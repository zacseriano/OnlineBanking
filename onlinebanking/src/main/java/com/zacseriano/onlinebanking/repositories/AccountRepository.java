package com.zacseriano.onlinebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.onlinebanking.models.account.Account;

/**
 * Interface que implementa o repositório de Conta, com métodos JPA CRUD.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	 
	/**
	 * Método para procurar um Conta pelo seu numero.
	 */ 
	Account findByNumber(String number);
	
}