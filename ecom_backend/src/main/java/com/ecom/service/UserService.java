package com.ecom.service;

import com.ecom.dto.response.JwtResponse;
import com.ecom.dtos.request.SigninRequest;
import com.ecom.dtos.request.SignupRequest;
import com.ecom.dtos.response.UserDto;


public interface UserService {

	UserDto getProfile(String email);
	UserDto signup(SignupRequest request);
	JwtResponse signin(SigninRequest request);
	
}
