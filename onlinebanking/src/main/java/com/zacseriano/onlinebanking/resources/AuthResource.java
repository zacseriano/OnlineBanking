package com.zacseriano.onlinebanking.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.repositories.UserRepository;
import com.zacseriano.onlinebanking.security.AuthForm;
import com.zacseriano.onlinebanking.security.ImplementsUserDetailsService;
import com.zacseriano.onlinebanking.security.TokenDto;
import com.zacseriano.onlinebanking.security.TokenService;

/**
 * SpringBoot RestController que implementa os end-points de autenticação/autorização da API
 */  
@RestController
public class AuthResource {
	
	@Autowired
	ImplementsUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	/**
	 * Método que gera tokens válidos de JWT para autorização de Clientes/Gestor na API
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@PostMapping(value = "/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthForm form){
		UsernamePasswordAuthenticationToken loginData = form.converter();
		
		try {
			Authentication authentication = authManager.authenticate(loginData);
			String token;
			if(form.verifyUser(userRepository))
				token = tokenService.generateToken(authentication);
			else throw new UserNotFoundException();				
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
