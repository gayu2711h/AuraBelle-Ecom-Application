package com.ecom.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.custom_exceptions.ResourceNotFoundException;
import com.ecom.dtos.request.ProductRequest;
import com.ecom.dtos.response.ProductDto;
import com.ecom.entities.Category;
import com.ecom.entities.Product;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService
{
    private static final int LOW_STOCK_THRESHOLD = 5;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private ProductDto toDto(Product product) 
    {
        ProductDto dto = modelMapper.map(product, ProductDto.class);
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setStockStatus(resolveStockStatus(product.getQuantity()));
        return dto;
    }
    
    private String resolveStockStatus(Integer quantity) {
        if (quantity == null || quantity <= 0) return "OUT_OF_STOCK";
        if (quantity <= LOW_STOCK_THRESHOLD) return "LOW_STOCK";
        return "IN_STOCK";
    }

    private double computeSpecialPrice(double price, double discount) 
    {
        return discount > 0 ? price - (price * discount / 100) : price;
    }
    
	@Override
	@Transactional
	public ProductDto createProduct(ProductRequest request) 
	{
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setImage(null);
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setSpecialPrice(computeSpecialPrice(request.getPrice(), request.getDiscount()));
        product.setCategory(category);

        return toDto(productRepository.save(product));		
	}
    
}
