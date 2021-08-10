package com.zacseriano.onlinebanking.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.UserRepository;

/*
 * Classe que implementa o UserDetailsService que é utilizado na classe WebSecurityConfig para autorizar e autenticar 
 * um usuário.
 */
@Service
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;	
	
	/*
	 * Método que encontra um usuário pelo email informado e checa se o mesmo existe,
	 * retornando o Usuário, quando encontrado.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		
		if(user == null)
			throw new UsernameNotFoundException("Usuário não encontrado!");
		else
			return user;
	}	
}
