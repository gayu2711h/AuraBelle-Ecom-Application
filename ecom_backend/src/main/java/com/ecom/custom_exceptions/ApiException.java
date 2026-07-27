package com.ecom.custom_exceptions;

//generic business rule violation (Eg duplicate email, insufficient stock ...)
public class ApiException extends RuntimeException {
	public ApiException(String mesg) {
		super(mesg);
	}
}
