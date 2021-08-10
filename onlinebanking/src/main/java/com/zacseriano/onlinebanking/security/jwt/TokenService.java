package com.zacseriano.onlinebanking.security.jwt;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.zacseriano.onlinebanking.models.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * Classe que configura o service de criação de token com o Jwts.builder()
 */
@Service
public class TokenService {
	
	private long expiration = 600000;
	
	private String secret = "rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!";

	public String generateToken(Authentication authentication) {
			
		User logged = (User) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + expiration);
		
		return Jwts.builder()
				.setIssuer("Online Banking")
				.setSubject(logged.getEmail().toString())
				.setIssuedAt(today)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

}

