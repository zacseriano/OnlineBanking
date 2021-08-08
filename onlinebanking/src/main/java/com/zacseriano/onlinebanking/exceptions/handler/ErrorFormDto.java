package com.zacseriano.onlinebanking.exceptions.handler;
/*
 * DTO usado nos Handlers de Exceção
 */
public class ErrorFormDto {
	
	private String field;
	private String error;
	
	public ErrorFormDto(String field, String error) {
		this.field = field;
		this.error = error;
	}

	public String getField() {
		return field;
	}

	public String getError() {
		return error;
	}
	
}
