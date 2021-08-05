package com.zacseriano.onlinebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.onlinebanking.models.user.User;

/**
 * Interface que implementa o repositório de Cliente, com métodos JPA CRUD.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	 
	/**
	 * Método para procurar um Cliente pelo seu email.
	 */ 
	User findByEmail(String email);
	
}