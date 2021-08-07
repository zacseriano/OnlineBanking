package com.zacseriano.onlinebanking.repositories;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.zacseriano.onlinebanking.models.account.Account;
import com.zacseriano.onlinebanking.models.user.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void shouldLoadAccountAtNumberSearch() {
		
		String email = "default@email.com";
		String password = "123456";
		String name = "John Doe";
		
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);
		
		String number = "1234-5";
		BigDecimal balance = new BigDecimal("0");
		
		Account account = new Account();
		account.setNumber(number);
		account.setBalance(balance);
		account.setUser(user);
		
		em.persist(user);		
		em.persist(account);
		
		Account test = repository.findByNumber(number);
		Assert.assertNotNull(test);
		Assert.assertEquals(number, test.getNumber());
	}
	
	@Test
	public void shouldNotLoadAccountNotYetRegisteredAtNumberSearch() {
		
		String number = "9999-9";
		Account test = repository.findByNumber(number);
		Assert.assertNull(test);
	}

}
