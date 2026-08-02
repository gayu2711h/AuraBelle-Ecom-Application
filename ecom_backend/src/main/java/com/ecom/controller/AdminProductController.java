package com.ecom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.dto.response.ImageUploadResponse;
import com.ecom.dtos.request.ProductRequest;
import com.ecom.dtos.response.ApiResponse;
import com.ecom.service.ProductService;
import com.ecom.service.S3ImageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController 
{
	private final ProductService productService;
    private final S3ImageService s3ImageService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody ProductRequest request) 
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(productService.createProduct(request)));
	}
	
    // Uploads a product image to S3 and stores the resulting public URL on the Product entity
    @PostMapping(value = "/{id}/image" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String imageUrl = s3ImageService.uploadProductImage(file);
        productService.updateProductImage(id, imageUrl);
        return ApiResponse.success(new ImageUploadResponse(id, imageUrl));
    }
}
