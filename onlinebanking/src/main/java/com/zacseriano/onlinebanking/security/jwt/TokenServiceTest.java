package com.zacseriano.onlinebanking.security.jwt;

import java.util.Date;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.zacseriano.onlinebanking.models.user.test.UserTest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/*
 * TokenService utilizado no ambiente de testes para gerar um JWT para um UserTest.
 */
@Profile(value="test")
@Service
public class TokenServiceTest {
	
	private long expiration = 600000;
	
	private String secret = "rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!";

	public String generateToken(Authentication authentication) {
			
		UserTest logged = (UserTest) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + expiration);
		
		return Jwts.builder()
				.setIssuer("Online Banking")
				.setSubject(logged.getUsername())
				.setIssuedAt(today)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

}

