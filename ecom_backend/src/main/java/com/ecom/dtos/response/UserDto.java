package com.ecom.dtos.response;

import com.ecom.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	    private Long userId;
	    private String userName;
	    private String email;
	    private Role role;
}

