package com.ecom.service;

import com.ecom.dtos.response.UserDto;

public interface UserService {

	UserDto getProfile(String email);
}
