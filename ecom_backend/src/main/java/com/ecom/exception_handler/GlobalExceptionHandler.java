package com.ecom.exception_handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecom.custom_exceptions.ApiException;
import com.ecom.dtos.ApiResponse;

/*
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * - supplies common exception handling advice to all rest controllers
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


	// ApiException -> business rule violation (dup email, dup category ..) - 409
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<?> handleApiException(ApiException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Failed", e.getMessage()));
	}

}
