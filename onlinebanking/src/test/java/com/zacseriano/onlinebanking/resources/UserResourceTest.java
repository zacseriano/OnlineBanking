package com.zacseriano.onlinebanking.resources;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.zacseriano.onlinebanking.exceptions.user.ExistingUserException;
import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.UserRepository;
/*
 * Classe de testes JUnit 4 responsável por testar todos os métodos do controller UserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository repository;
	
	/*
	 * Configura para antes do teste, salvar um usuário que servirá como modelo
	 * para comparação para evitar duplicidade
	 */
	@Before
	public void setup() throws UsernameNotFoundException, ParseException {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode("123456");
			
		User user = new User("registered@email.com", password, "Dohn Joe");		
		Mockito.when(repository.findByEmail(user.getEmail()))
		.thenReturn(user);
	}
	/*
	 * Testa um registro válido de usuário
	 */
	@Test
	public void shouldReturn201AtUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"default@email.com\",\"password\":\"123456\",\"name\":\"John Doe\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(201));
	}
	
	@Test
	public void shouldReturn400AtExistingUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"registered@email.com\",\"password\":\"123456\",\"name\":\"John Wrong\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof ExistingUserException));
	}
	
	@Test
	public void shouldReturn400AtInvalidEmailUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"a\",\"password\":\"123456\",\"name\":\"John Wrong\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));;
	}
	
	@Test
	public void shouldReturn400AtNullEmailUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"\",\"password\":\"123456\",\"name\":\"John Wrong\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));;
	}
	
	@Test
	public void shouldReturn400AtNullPasswordUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"default@email.com\",\"password\":\"\",\"name\":\"John Wrong\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));;
	}
	
	@Test
	public void shouldReturn400AtSmallPasswordUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"default@email.com\",\"password\":\"123\",\"name\":\"John Wrong\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));;
	}
	
	@Test
	public void shouldReturn400AtInvalidNameUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"default@email.com\",\"password\":\"123\",\"name\":\"A\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));;
	}
	
	@Test
	public void shouldReturn400AtNullNameUserRegister() throws Exception {
		URI uri = new URI("/users");
		
		String json = "{\"email\":\"default@email.com\",\"password\":\"123\",\"name\":\"\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));;
	}	

}
