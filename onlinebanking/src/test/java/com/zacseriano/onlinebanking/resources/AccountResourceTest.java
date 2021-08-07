package com.zacseriano.onlinebanking.resources;

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
import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.models.user.UserTest;
import com.zacseriano.onlinebanking.models.user.UserTestFactory;
import com.zacseriano.onlinebanking.repositories.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class AccountResourceTest {
	
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
		String email = "valid@email.com";
		String name = "John Doe";
			
		User user = new User(email, password, name);
		
		Mockito.when(userRepository.findByEmail(user.getEmail()))
		.thenReturn(user);

	}
	
	@Test
	public void shouldReturn201AtAccountCreation() throws Exception {
		URI uri = new URI("/account");
		String json = "{\"number\":\"1234-5\",\"balance\":\"0.0\",\"userEmail\":\"valid@email.com\"}";
		
		String password = "123456";
		String email = "valid@email.com";
		AuthForm form = new AuthForm(email, password);
		
		BDDMockito.given(userDetailsService.loadUserByUsername(Mockito.any(String.class)))
		.willReturn(getMockUserTest());
		
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
		String email = "valid@email.com";
		String name = "John Doe";
		User user = new User(email, password, name);
		return UserTestFactory.create(user);
	}
}
