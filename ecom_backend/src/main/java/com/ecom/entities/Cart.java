package com.ecom.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id")
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , unique=true , nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<CartItems> cartItems = new ArrayList<>();

    private Double totalPrice = 0.0;
}
