package com.qrbased.cafe.util;

import java.util.Set;

import com.qrbased.cafe.dto.Role;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String userName;
    private String userEmail;
    private Role userRole;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role set) {
		this.userRole = set;
	}

    
}

