package com.miura.domain.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignupForm {
	@NotBlank
	@Email
	private String userId;
	@NotBlank
	private String password;
	@NotBlank
	private String userName;
}
