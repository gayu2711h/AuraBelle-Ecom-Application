package com.ecom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dtos.request.ProductRequest;
import com.ecom.dtos.response.ApiResponse;
import com.ecom.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController 
{
	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody ProductRequest request) 
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(productService.createProduct(request)));
	}
}
