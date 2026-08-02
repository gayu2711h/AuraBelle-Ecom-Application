package com.ecom.service;

import com.ecom.dtos.request.ProductRequest;
import com.ecom.dtos.response.ProductDto;

import jakarta.validation.Valid;

public interface ProductService 
{

	ProductDto createProduct(@Valid ProductRequest request);

	ProductDto updateProductImage(Long id, String imageUrl);

}
