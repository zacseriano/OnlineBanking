package com.zacseriano.onlinebanking.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zacseriano.onlinebanking.exceptions.user.ExistingUserException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.UserRepository;
import com.zacseriano.onlinebanking.resources.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * SpringBoot RestController que implementa o endpoint de criação de usuário da API
 */  
@RestController
@Api(value="Cadastro de Usuários.")
public class UserResource {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Método que recebe as credenciais e cadastra um usuário, caso o seu email não exista no banco de dados
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: Já existe um Usuário cadastrado
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@PostMapping(value="/users")
	@ApiOperation(value="Registra um usuário no banco de dados.")
	public ResponseEntity<UserDto> registerUser(@RequestBody @Valid User user) {
		if(userRepository.findByEmail(user.getEmail()) != null) throw new ExistingUserException();
		
		userRepository.save(user);
		
		return ResponseEntity.created(null).body(new UserDto(user));	
		
	}
	
}
