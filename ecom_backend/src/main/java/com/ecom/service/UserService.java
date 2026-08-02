package com.ecom.service;


import com.ecom.dtos.request.SignupRequest;
import com.ecom.dtos.response.UserDto;

public interface UserService {


	UserDto signup(SignupRequest request);
	UserDto getProfile(String email);
}
