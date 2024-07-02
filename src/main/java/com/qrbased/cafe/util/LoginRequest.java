package com.qrbased.cafe.util;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.Email;

public class LoginRequest {

	@NotEmpty(message="required")
	@Email
	private String email;
	
	@NotEmpty(message="required")
	private String password;

	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

