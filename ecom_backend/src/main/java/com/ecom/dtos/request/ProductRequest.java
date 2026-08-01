package com.ecom.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest 
{
    @NotBlank
    @Size(min = 3, message = "Product name must contain atleast 3 characters")
    private String productName;

    // relative path, e.g. western-wear/western-wear-03.jpg
    private String image;

    @NotBlank
    @Size(min = 6, message = "Product description must contain atleast 6 characters")
    private String description;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Positive
    private Double price;

    @Min(0)
    @Max(100)
    private double discount;

    @NotNull
    private Long categoryId;
	

}
