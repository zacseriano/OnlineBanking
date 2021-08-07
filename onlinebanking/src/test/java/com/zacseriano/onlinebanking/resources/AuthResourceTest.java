package com.zacseriano.onlinebanking.resources;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.zacseriano.onlinebanking.models.user.User;
import com.zacseriano.onlinebanking.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository repository;
	
	@Before
	public void setup() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode("123456");
		String email = "valid@email.com";
		String name = "John Doe";
		
		User user = new User(email, password, name);
		
		Mockito.when(repository.findByEmail(user.getEmail()))
		.thenReturn(user);
	}

	@Test
	public void shouldReturn400AtInvalidAuthData() throws Exception {
		URI uri = new URI("/auth");
		String json = "{\"email\":\"invalid@email.com\",\"password\":\"aaaaaa\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
	}
	
	@Test
	public void shouldReturn200AtValidAuthData() throws Exception {
		URI uri = new URI("/auth");
		
		String json = "{\"email\":\"valid@email.com\",\"password\":\"123456\"}";
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(200));
	}	
}
