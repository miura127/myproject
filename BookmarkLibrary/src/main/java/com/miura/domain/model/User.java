package com.miura.domain.model;

import lombok.Data;

@Data
public class User {
	private String userId;
	private String password;
	private String userName;
	private String role;
}
