package com.zacseriano.onlinebanking.exceptions.handler;
/*
 * DTO usado nos Handlers de Exceção
 */
public class ErrorDto {

	private String message;

	public ErrorDto(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
