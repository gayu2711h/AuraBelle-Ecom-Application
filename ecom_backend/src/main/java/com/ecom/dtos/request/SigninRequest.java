package com.ecom.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninRequest {

	 @NotBlank(message = "Email is Required !!")
	 @Email(message = "Invalid Email Format!!")
	 @Size(max = 50)
    private String email;

	 @NotBlank(message = "Password is Required !!")
	 @Size(min = 6, max = 120, message = "Password must be at least 6 characters")
	 @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})",message = "Invalid Password Format!!")
	 private String password;
}
