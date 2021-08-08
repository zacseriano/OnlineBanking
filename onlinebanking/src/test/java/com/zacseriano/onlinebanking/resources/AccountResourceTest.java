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
import com.zacseriano.onlinebanking.security.AuthForm;
import com.zacseriano.onlinebanking.security.ImplementsUserDetailsService;
import com.zacseriano.onlinebanking.security.TokenServiceTest;
import com.zacseriano.onlinebanking.exceptions.account.AccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.account.DestinationAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeSourceBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.SourceAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.user.UnauthorizedUserException;
import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.models.account.Account;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.models.user.UserTest;
import com.zacseriano.onlinebanking.models.user.UserTestFactory;
import com.zacseriano.onlinebanking.repositories.AccountRepository;
/*
 * Classe de testes JUnit 4 responsável por testar todos os métodos do controller AccountResource
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
	static final BigDecimal HUND = new BigDecimal("100");
	
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
	
	@Before
	public void setup() throws UsernameNotFoundException, ParseException {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode("123456");
			
		User user = new User(EMAIL, password, NAME);		
		Mockito.when(userRepository.findByEmail(user.getEmail()))
		.thenReturn(user);
		
		User alternateUser = new User(ALTERNATE_EMAIL, password, ALTERNATE_NAME);		
		Mockito.when(userRepository.findByEmail(alternateUser.getEmail()))
		.thenReturn(alternateUser);
		
		Account account = new Account(NUMBER, HUND, user);		
		Mockito.when(accRepository.findByNumber(account.getNumber()))
		.thenReturn(account);
		
		Account alternateAccount = new Account(ALTERNATE_NUMBER, HUND, alternateUser);		
		Mockito.when(accRepository.findByNumber(alternateAccount.getNumber()))
		.thenReturn(alternateAccount);
		
		BDDMockito.given(userDetailsService.loadUserByUsername(Mockito.any(String.class)))
		.willReturn(getMockUserTest());
	}
	
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
	
	@Test
	public void shouldReturn400AtNegativeAccountBalanceCreation() throws Exception {
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
	
	@Test
	public void shouldReturn400AtUserNotFoundAtAccountCreation() throws Exception {
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
				.is(400));
	}
	
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
	
	public String createAuthenticationToken(AuthForm form){
		
		UsernamePasswordAuthenticationToken loginData = form.converter();
		
		Authentication authentication = authManager.authenticate(loginData);
		String token;
		if(form.verifyUser(userRepository))
			token = tokenService.generateToken(authentication);
		else throw new UserNotFoundException();				
			
		return token;

	}
	
	private UserTest getMockUserTest() throws ParseException {
	
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode("123456");

		User user = new User(EMAIL, password, NAME);
		return UserTestFactory.create(user);
	}
}
