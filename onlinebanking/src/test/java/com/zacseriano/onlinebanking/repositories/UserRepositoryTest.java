package com.zacseriano.onlinebanking.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.zacseriano.onlinebanking.models.user.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void shouldLoadUserAtEmailSearch() {
		
		String email = "default@email.com";
		String password = "123456";
		String name = "John Doe";
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);
		em.persist(user);
		
		User test = repository.findByEmail(email);
		Assert.assertNotNull(test);
		Assert.assertEquals(email, test.getEmail());
	}
	
	@Test
	public void shouldNotLoadUserNotYetRegisteredAtEmailSearch() {
		
		String email = "invalid@email.com";
		User test = repository.findByEmail(email);
		Assert.assertNull(test);
	}

}