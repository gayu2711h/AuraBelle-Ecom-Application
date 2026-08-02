package com.ecom.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.custom_exceptions.ResourceNotFoundException;
import com.ecom.dtos.response.UserDto;
import com.ecom.entities.UserEntity;
import com.ecom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public UserDto getProfile(String email) {
	 UserEntity user = userRepository.findByEmail(email)
             .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
      return modelMapper.map(user, UserDto.class);
	}

}
