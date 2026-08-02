package com.ecom.exception_handler;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		System.out.println("in catch @Valid P.L ");
		//1. Extract List of rejected field errors
		List<FieldError> fieldErrors=e.getFieldErrors();
		
		 Map<String, String> fieldErrorMap = fieldErrors.stream() 
		 .collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage, (v1,v2) -> v1+","+v2));
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)// SC 400
				.body(fieldErrorMap);
	}

}
