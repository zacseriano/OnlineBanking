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
import com.zacseriano.onlinebanking.models.account.AccountTransferForm;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
import com.zacseriano.onlinebanking.repositories.UserRepository;
import com.zacseriano.onlinebanking.resources.dto.AccountBalanceDto;
import com.zacseriano.onlinebanking.resources.dto.AccountDto;
import com.zacseriano.onlinebanking.resources.dto.AccountTransferDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * SpringBoot RestController que implementa os endpoints de operações envolvendo contas
 */  
@RestController
@RequestMapping(value="/account")
@Api(value="Operações em Contas.")
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
	@ApiOperation(value="Cria uma conta única.")
	public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountForm form) {
		
		Account account = form.converter(userRepository, accountRepository);
		accountRepository.save(account);
		
		return ResponseEntity.created(null).body(new AccountDto(account));
				
	}
	/**
	 * Método que mostra o saldo de uma conta vinculada a um email informado, ocorrem 
	 * verificações de existência do usuário e da conta e se o usuário é realmente o 
	 * responsável pela conta. Retorna o saldo caso tudo esteja em conformidade.
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
	@PostMapping("/balance")
	@ApiOperation(value="Mostra o saldo de uma conta.")
	public ResponseEntity<AccountBalanceDto> showBalance(@RequestBody @Valid AccountBalanceForm form){
		
		Account account = form.converter(userRepository, accountRepository);
		
		return ResponseEntity.ok(new AccountBalanceDto(account));
	}
	/**
	 * Método que executa uma transferência entre contas, com uma conta de origem
	 * vinculada a um usuário que foi informado na requisição, e uma conta destino
	 * válida. Ocorrem verificações de existência de usuário e contas. E também se
	 * o usuário informado é responsável pela conta. Retorna um relatório sobre a
	 * transferência caso tudo esteja em conformidade.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando 
	 * 						ou em desconformidade
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@PostMapping("/transfer")
	@Transactional	
	@SuppressWarnings("unused")
	@ApiOperation(value="Executa uma transferência entre contas.")
	public ResponseEntity<AccountTransferDto> transfer(@RequestBody @Valid AccountTransferForm form){
			
		Account sourceAccount = form.convertSource(userRepository, accountRepository);

		Account destinationAccount = form.convertDestination(userRepository, accountRepository);
		
		return ResponseEntity.ok(new AccountTransferDto(form, userRepository));
	}

}
