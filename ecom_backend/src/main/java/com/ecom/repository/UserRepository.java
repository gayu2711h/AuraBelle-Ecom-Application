package com.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	// check if user already exists by email or username - to reject dup signup
	boolean existsByEmailOrUserName(String email, String userName);

	boolean existsByEmail(String email);
	// used by CustomUserDetailsService to load user for authentication
	Optional<UserEntity> findByEmail(String email);
	
	boolean existsByUserName(String userName);
}
