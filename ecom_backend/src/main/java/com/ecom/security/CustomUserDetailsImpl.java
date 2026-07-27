package com.ecom.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecom.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * Spring Security's view of a logged in user - wraps the fields
 * needed for authentication + authorization (built from UserEntity)
 */
@Getter
@AllArgsConstructor
public class CustomUserDetailsImpl implements UserDetails {
	private Long userId;
	private String email;
	private String password;
	private Role role;
	private String userName;

	/*
	 * GrantedAuthority - core i/f used by the Authorization manager to perform
	 * RBAC (role based access control).
	 * NOTE - while using hasRole("ADMIN") Spring expects authority "ROLE_ADMIN"
	 * (our Role enum constants are already prefixed with ROLE_)
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		// email is the login identifier
		return this.email;
	}
}
