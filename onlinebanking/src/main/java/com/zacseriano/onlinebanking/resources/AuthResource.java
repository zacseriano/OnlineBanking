package com.zacseriano.onlinebanking.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.repositories.UserRepository;
import com.zacseriano.onlinebanking.security.config.ImplementsUserDetailsService;
import com.zacseriano.onlinebanking.security.form.AuthForm;
import com.zacseriano.onlinebanking.security.jwt.TokenDto;
import com.zacseriano.onlinebanking.security.jwt.TokenService;

import io.swagger.annotations.ApiOperation;

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
	 * Método que gera tokens válidos de JWT para autorização de Usuários na API
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
	@ApiOperation(value="Valida as credenciais e informa um Token JWT válido para autorização na API.")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthForm form){
		
		if(form.verifyUser(userRepository)) {
		UsernamePasswordAuthenticationToken loginData = form.converter();

		Authentication authentication = authManager.authenticate(loginData);
		String token;
		//if(form.verifyUser(userRepository))
				token = tokenService.generateToken(authentication);
		//else throw new UserNotFoundException();				
			
		String email = form.getEmail();
		String name = userRepository.findByEmail(email).getName();
			
		return ResponseEntity.ok(new TokenDto(name, email, token));
		} else throw new UserNotFoundException();
	}
}
