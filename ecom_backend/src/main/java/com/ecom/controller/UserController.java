package com.ecom.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecom.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	// depcy - ctor based D.I
	private final UserService userService;

}
