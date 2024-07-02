package com.qrbased.cafe.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	 @Override
	    public void commence(HttpServletRequest request, HttpServletResponse response,
	                         AuthenticationException authException) throws IOException, ServletException {

	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"responseCode\":\"401\",\"message\":\"You are unauthorized\"}");
	        response.getWriter().flush();
	        response.getWriter().close();
	    }

}
