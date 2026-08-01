package com.ecom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration // declares java config class - to declare spring beans
@EnableWebSecurity // to enable spring web security
@EnableMethodSecurity // to enable method level authorization rules (@PreAuthorize)
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final CustomUserDetailsServiceImpl userDetailsService;
	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	SecurityFilterChain customizeSecurityFilterChain(HttpSecurity http) throws Exception {
		// 1. disable CSRF protection - stateless JSON API, no cookies/session
		http.csrf(csrf -> csrf.disable());
		// 2. Disable HttpSession creation - auth state is carried in the JWT, per request
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		/*
		 * 3. Define URL based authorization rules
		 * 3.1 public end points - swagger , register , login , browse catalog
		 * 3.2 /admin/** - ROLE_ADMIN only
		 * 3.3 remaining all end points - only authentication required
		 */
		http.authorizeHttpRequests(request -> request
                // CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Unified single-entry auth - register/login, no separate admin login
                .requestMatchers("/auth/**").permitAll()

                // Swagger / OpenAPI
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                // Public, guest-accessible catalog browsing
                .requestMatchers(HttpMethod.GET, "/categories/**", "/products/**").permitAll()

                // Everything under /admin/** requires ROLE_ADMIN (categories/products/orders/users/dashboard
                // management all live here - see Admin*Controller classes)
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Every other endpoint (cart, addresses, checkout, orders, users/profile)
                // just requires a signed-in user, customer or admin
                .anyRequest().authenticated());
		
		// 4. Plug in the JWT filter before Spring Security's own auth filter
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/*
	 * Configure Password encoder bean
	 * - BCryptPasswordEncoder - SHA with salt
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * DaoAuthenticationProvider - ties together UserDetailsService + PasswordEncoder
	 * so the AuthenticationManager can verify email/password on login
	 */
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
