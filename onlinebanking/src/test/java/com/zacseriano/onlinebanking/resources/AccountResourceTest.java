package com.zacseriano.onlinebanking.resources;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;

import com.zacseriano.onlinebanking.repositories.UserRepository;
import com.zacseriano.onlinebanking.security.config.ImplementsUserDetailsService;
import com.zacseriano.onlinebanking.security.form.AuthForm;
import com.zacseriano.onlinebanking.security.jwt.TokenServiceTest;
import com.zacseriano.onlinebanking.exceptions.account.AccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.account.DestinationAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeSourceBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.SmallAccountNumberException;
import com.zacseriano.onlinebanking.exceptions.account.SourceAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.user.UnauthorizedUserException;
import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.models.account.Account;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.models.user.test.UserTest;
import com.zacseriano.onlinebanking.models.user.test.UserTestFactory;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
/*
 * Classe de testes JUnit 4 respons??vel por testar todos os m??todos do controller AccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class AccountResourceTest {
	
	static final String EMAIL = "valid@email.com";
	static final String ALTERNATE_EMAIL = "default@email.com";
	static final String PASSWORD = "123456";
	static final String NAME = "John Doe";
	static final String ALTERNATE_NAME = "Dohn Joe";
	static final String NUMBER = "1234-5";
	static final String ALTERNATE_NUMBER = "7777-7";
	static final BigDecimal HUNDRED = new BigDecimal("100");
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ImplementsUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenServiceTest tokenService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private AccountRepository accRepository;
	/*
	 * Antes da execu????o do teste, algumas configura????es s??o executadas pra simular
	 * o funcionamento real da API, cadastrando dois usu??rios diferentes para duas
	 * contas diferentes, afim de testar v??rias possibilidades no funcionamento da API.
	 * Essas configura????es pr??vias s??o sinalizadas pela anota????o @Before.
	 */
	@Before
	public void setup() throws UsernameNotFoundException, ParseException {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode(PASSWORD);
			
		User user = new User(EMAIL, password, NAME);		
		Mockito.when(userRepository.findByEmail(user.getEmail()))
		.thenReturn(user);
		
		User alternateUser = new User(ALTERNATE_EMAIL, password, ALTERNATE_NAME);		
		Mockito.when(userRepository.findByEmail(alternateUser.getEmail()))
		.thenReturn(alternateUser);
		
		Account account = new Account(NUMBER, HUNDRED, user);		
		Mockito.when(accRepository.findByNumber(account.getNumber()))
		.thenReturn(account);
		
		Account alternateAccount = new Account(ALTERNATE_NUMBER, HUNDRED, alternateUser);		
		Mockito.when(accRepository.findByNumber(alternateAccount.getNumber()))
		.thenReturn(alternateAccount);
		/*
		 * Mockito que ser?? usado junto do MockMvc em v??rios testes com a finalidade 
		 * de gerar um JWT para as credenciais do User que ?? cadastrado nas 
		 * configura????es que rodam antes do funcionamento do teste, 
		 * sinalizados pela anota????o @Before.
		 */
		BDDMockito.given(userDetailsService.loadUserByUsername(Mockito.any(String.class)))
		.willReturn(getMockUserTest());
	}
	/*
	 * Testa uma cria????o de conta com dados v??lidos
	 */
	@Test
	public void shouldReturn201AtAccountCreation() throws Exception {
		URI uri = new URI("/account");
		String json = "{\"number\":\"1111-1\",\"balance\":\"0.0\"}";
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(201));
	}
	/*
	 * Testa uma cria????o de conta com n??mero inv??lido
	 */
	@Test
	public void shouldReturn400AtInvalidNumberAccountCreation() throws Exception {
		URI uri = new URI("/account");
		String json = "{\"number\":\"11\",\"balance\":\"0\"}";
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof SmallAccountNumberException));
	}
	/*
	 * Testa uma cria????o de conta com saldo negativo
	 */
	@Test
	public void shouldReturn400AtNegativeBalanceAccountCreation() throws Exception {
		URI uri = new URI("/account");
		String json = "{\"number\":\"1111-1\",\"balance\":\"-10\"}";
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof NegativeBalanceException));
	}
	/*
	 * Testa uma consulta de saldo v??lida
	 */
	@Test
	public void shouldReturn200AtAccountBalance() throws Exception {
		
		URI uri = new URI("/account/balance");
		String json = "{\"number\":\"1234-5\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200));		
	}
	/*
	 * Testa uma consulta de saldo inv??lida
	 */
	@Test
	public void shouldReturn400AtAccountNotFoundAtAccountBalance() throws Exception {
		
		URI uri = new URI("/account/balance");
		String json = "{\"number\":\"3333-3\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof AccountNotFoundException));		
	}
	/*
	 * Testa uma consulta de saldo por um usu??rio que n??o ?? o dono da conta
	 */
	@Test
	public void shouldReturn400AtUnauthorizedUserAtAccountBalance() throws Exception {
		
		URI uri = new URI("/account/balance");
		String json = "{\"number\":\"7777-7\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedUserException));		
	}
	/*
	 * Testa uma transfer??ncia v??lida
	 */
	@Test
	public void shouldReturn200AtAccountTransfer() throws Exception {
		
		URI uri = new URI("/account/transfer");
		String json = "{\"amount\":\"90.00\",\"source_account_number\":"
				+ " \"1234-5\", \"destination_account_number\": \"7777-7\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200));		
	}
	/*
	 * Testa se o saldo da conta de origem foi subtra??do em uma transfer??ncia v??lida
	 */
	@Test
	public void shouldSubtractSourceAccountBalanceAtSucessfulTransfer() throws Exception {
		
		URI uri = new URI("/account/transfer");
		String json = "{\"amount\":\"90.00\",\"source_account_number\":"
				+ " \"1234-5\", \"destination_account_number\": \"7777-7\"}";
		
		BigDecimal previousBalance = accRepository.findByNumber("1234-5").getBalance();
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andExpect(result -> assertTrue(-1 == accRepository.findByNumber(NUMBER).getBalance()
				.compareTo(previousBalance)));		
	}
	/*
	 * Testa se o saldo da conta de destino sofreu acr??scimo em uma transfer??ncia v??lida
	 */
	@Test
	public void shouldAddDestinationAccountBalanceAtSucessfulTransfer() throws Exception {
		
		URI uri = new URI("/account/transfer");
		String json = "{\"amount\":\"90.00\",\"source_account_number\":"
				+ " \"1234-5\", \"destination_account_number\": \"7777-7\"}";
		
		BigDecimal previousBalance = accRepository.findByNumber(ALTERNATE_NUMBER).getBalance();
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);		
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200))
		.andExpect(result -> assertTrue(1 == accRepository.findByNumber(ALTERNATE_NUMBER)
				.getBalance().compareTo(previousBalance)));		
	}
	/*
	 * Testa uma transfer??ncia feita por um usu??rio que n??o ?? dono da conta de origem
	 */
	@Test
	public void shouldReturn400AtUnauthorizedUserAtTransfer() throws Exception {
		
		URI uri = new URI("/account/transfer");
		String json = "{\"amount\":\"90.00\",\"source_account_number\":"
				+ " \"7777-7\", \"destination_account_number\": \"1234-5\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof UnauthorizedUserException));		
		
	}
	/*
	 * Testa uma transfer??ncia que deixar?? a conta de origem com saldo negativo
	 */
	@Test
	public void shouldReturn400AtNegativeSourceBalanceTransfer() throws Exception {
		
		URI uri = new URI("/account/transfer");
		String json = "{\"amount\":\"500.00\",\"source_account_number\":"
				+ " \"1234-5\", \"destination_account_number\": \"7777-7\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof NegativeSourceBalanceException));		
		
	}
	/*
	 * Testa uma transfer??ncia que n??o encontra uma conta de origem
	 */
	@Test
	public void shouldReturn400AtSourceAccountNotFoundTransfer() throws Exception {
		
		URI uri = new URI("/account/transfer");
		String json = "{\"amount\":\"90.00\",\"source_account_number\":"
				+ " \"3333-3\", \"destination_account_number\": \"1234-5\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof SourceAccountNotFoundException));		
		
	}
	/*
	 * Testa uma transfer??ncia que n??o encontra uma conta de destino
	 */
	@Test
	public void shouldReturn400AtDestinationAccountNotFoundTransfer() throws Exception {
		
		URI uri = new URI("/account/transfer");
		String json = "{\"amount\":\"90.00\",\"source_account_number\":"
				+ " \"1234-5\", \"destination_account_number\": \"3333-3\"}";		
		
		AuthForm form = new AuthForm(EMAIL, PASSWORD);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.header("Authorization", "Bearer " + createAuthenticationToken(form))
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof DestinationAccountNotFoundException));		
		
	}
	/*
	 * M??todo utilizado para gerar um JWT v??lido para este ambiente de testes
	 */
	public String createAuthenticationToken(AuthForm form){
		
		UsernamePasswordAuthenticationToken loginData = form.converter();
		
		Authentication authentication = authManager.authenticate(loginData);
		String token;
		if(form.verifyUser(userRepository))
			token = tokenService.generateToken(authentication);
		else throw new UserNotFoundException();				
			
		return token;

	}
	/*
	 * M??todo utilizado para criar um usu??rio teste e posteriormente gerar um token JWT
	 */
	private UserTest getMockUserTest() throws ParseException {
	
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode(PASSWORD);

		User user = new User(EMAIL, password, NAME);
		return UserTestFactory.create(user);
	}
}
