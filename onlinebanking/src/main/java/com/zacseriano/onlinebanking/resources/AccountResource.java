package com.zacseriano.onlinebanking.resources;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zacseriano.onlinebanking.models.account.Account;
import com.zacseriano.onlinebanking.models.account.AccountBalanceForm;
import com.zacseriano.onlinebanking.models.account.AccountForm;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
import com.zacseriano.onlinebanking.repositories.UserRepository;
import com.zacseriano.onlinebanking.resources.dto.AccountBalanceDto;
import com.zacseriano.onlinebanking.resources.dto.AccountDto;

@RestController
@RequestMapping(value="/account")
public class AccountResource {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	/**
	 * Método que cria uma conta vinculada ao Id do Usuário, identificado pelo email, o saldo
	 * deve ser positivo e o número da conta tem de ser único.
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
	@PostMapping
	@Transactional
	public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountForm form) {
		
		Account account = form.converter(userRepository, accountRepository);
		accountRepository.save(account);
		
		return ResponseEntity.created(null).body(new AccountDto(account));
				
	}
	
	@PostMapping("/balance")
	public ResponseEntity<AccountBalanceDto> showBalance(@RequestBody @Valid AccountBalanceForm form){
		
		Account account = form.converter(userRepository, accountRepository);
		
		return ResponseEntity.ok(new AccountBalanceDto(account));
	}

}
