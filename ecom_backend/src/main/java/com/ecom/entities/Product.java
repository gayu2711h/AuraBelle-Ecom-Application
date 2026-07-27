package com.ecom.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@ToString
public class Product {

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Column(name="product_name",nullable = false)
    @Size(min = 3, message = "Product name must contain atleast 3 characters")
    private String productName;
    
    private String image;

    @NotBlank
    @Size(min = 6, message = "Product description must contain atleast 6 characters")
    private String description;
    
    @Column(nullable = false)
    @Min(0)
    private Integer quantity;
    
    @Positive
    @Column(nullable = false)
    private Double price;
    
    @Min(0)
    @Max(100)
    private double discount;
    
    @Column(name="special_price")
    private double specialPrice;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems = new ArrayList<>();
    
    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();
}
