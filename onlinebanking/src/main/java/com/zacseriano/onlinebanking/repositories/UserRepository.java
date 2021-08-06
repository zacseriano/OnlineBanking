package com.zacseriano.onlinebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zacseriano.onlinebanking.models.user.User;

/**
 * Interface que implementa o repositório de Usuário, com métodos JPA CRUD.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	 
	/**
	 * Método para procurar um Usuário pelo seu email.
	 */ 
	User findByEmail(String email);
	
}