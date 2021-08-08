package com.zacseriano.onlinebanking.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zacseriano.onlinebanking.exceptions.account.AccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.account.DestinationAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.account.ExistingAccountException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.NegativeSourceBalanceException;
import com.zacseriano.onlinebanking.exceptions.account.SourceAccountNotFoundException;
import com.zacseriano.onlinebanking.exceptions.user.ExistingUserException;
import com.zacseriano.onlinebanking.exceptions.user.UnauthorizedUserException;
import com.zacseriano.onlinebanking.exceptions.user.UserNotFoundException;


@RestControllerAdvice
public class ErrorHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ExistingUserException.class)
	public ErrorDto handle(ExistingUserException exception) {
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnauthorizedUserException.class)
	public ErrorDto handle(UnauthorizedUserException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserNotFoundException.class)
	public ErrorDto handle(UserNotFoundException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AccountNotFoundException.class)
	public ErrorDto handle(AccountNotFoundException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DestinationAccountNotFoundException.class)
	public ErrorDto handle(DestinationAccountNotFoundException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ExistingAccountException.class)
	public ErrorDto handle(ExistingAccountException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NegativeBalanceException.class)
	public ErrorDto handle(NegativeBalanceException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NegativeSourceBalanceException.class)
	public ErrorDto handle(NegativeSourceBalanceException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SourceAccountNotFoundException.class)
	public ErrorDto handle(SourceAccountNotFoundException exception) {
		
		String message = exception.getMessage();
		
		return new ErrorDto(message);
		
	}
	
}
