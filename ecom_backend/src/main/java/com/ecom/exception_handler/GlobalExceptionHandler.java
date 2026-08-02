package com.ecom.exception_handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecom.custom_exceptions.ApiException;
import com.ecom.dtos.response.ApiResponse;

/*
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * - supplies common exception handling advice to all rest controllers
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


	// ApiException -> business rule violation (dup email, dup category ..) - 409
	@ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) 
	{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ex.getMessage()));
    }

}
