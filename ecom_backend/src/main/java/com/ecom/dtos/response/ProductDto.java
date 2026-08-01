package com.ecom.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto
{
    private Long productId;
    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private Double price;
    private double discount;
    private double specialPrice;
    private Long categoryId;
    private String categoryName;
    private String stockStatus; // IN_STOCK | LOW_STOCK | OUT_OF_STOCK
}
