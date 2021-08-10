package com.zacseriano.onlinebanking.exceptions.handler.auth;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationHandler implements AuthenticationEntryPoint, AccessDeniedHandler{
	
	@Override
	public void commence (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
						AuthenticationException e) throws IOException, ServletException {
		ApiResponse response = new ApiResponse("Acesso Negado.");
		httpServletResponse.setStatus(401);
		httpServletResponse.setContentType("application/json");
		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, response);
		out.flush();
	}
	
	@Override
	public void handle (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException, ServletException {
		ApiResponse response = new ApiResponse("Acesso Negado");
		response.setMessage("Acesso Negado");
		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out,  response);
		out.flush();	
	}
	
}

