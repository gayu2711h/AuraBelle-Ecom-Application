package com.ecom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

//Customer-facing order history. Orders are CREATED via /checkout/initiate +
///checkout/verify-payment (CheckoutController), not here.
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController 
{

}
