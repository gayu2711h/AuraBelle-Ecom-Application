package com.ecom.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

//small helper to fetch the currently logged-in user's id/role in controllers/services
@Component
public class SecurityUtils {

	public static CustomUserDetailsImpl getLoggedInUser() {
		return (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static Long getLoggedInUserId() {
		return getLoggedInUser().getUserId();
	}
}
