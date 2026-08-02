package com.ecom.service;

import org.modelmapper.ModelMapper;
<<<<<<< HEAD
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.custom_exceptions.ApiException;
import com.ecom.custom_exceptions.ResourceNotFoundException;
import com.ecom.dtos.request.SignupRequest;
import com.ecom.dtos.response.UserDto;
import com.ecom.entities.Cart;
import com.ecom.entities.UserEntity;
import com.ecom.enums.Role;
import com.ecom.repository.CartRepository;
=======
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.custom_exceptions.ResourceNotFoundException;
import com.ecom.dtos.response.UserDto;
import com.ecom.entities.UserEntity;
>>>>>>> e2deeeca1c134b0c159ae3d09deab3c342bd29ae
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

    private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final ModelMapper modelMapper;

	
	@Override
	public UserDto getProfile(String email) {
	 UserEntity user = userRepository.findByEmail(email)
             .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
      return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto signup(SignupRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("An account with this email already exists");
        }
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new ApiException("This username is already taken");
        }

        UserEntity user = new UserEntity();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_CUSTOMER);

        UserEntity saved = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(saved);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return modelMapper.map(saved, UserDto.class);
	}

}
