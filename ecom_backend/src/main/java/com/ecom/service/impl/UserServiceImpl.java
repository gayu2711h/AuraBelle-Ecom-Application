package com.ecom.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.authentication.BadCredentialsException;
import com.ecom.custom_exceptions.ApiException;
import com.ecom.custom_exceptions.ResourceNotFoundException;
import com.ecom.dto.response.JwtResponse;
import com.ecom.dtos.request.SigninRequest;
import com.ecom.dtos.request.SignupRequest;
import com.ecom.dtos.response.UserDto;
import com.ecom.entities.Cart;
import com.ecom.entities.UserEntity;
import com.ecom.enums.Role;
import com.ecom.repository.CartRepository;
import com.ecom.repository.UserRepository;
import com.ecom.security.JwtUtils;
import com.ecom.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final CartRepository cartRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtil;
	
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

	@Override
	public JwtResponse signin(SigninRequest request) {
		try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                  System.out.println("Authentication Successfull");
        } catch (Exception ex) {
            throw new BadCredentialsException("Invalid email or password");
        }

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getUserId());

        System.out.println("generated Token :"+token);
        return new JwtResponse(token, user.getUserId(), user.getUserName(), user.getEmail(), user.getRole().name());
	}

}
