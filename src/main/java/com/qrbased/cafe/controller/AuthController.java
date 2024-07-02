package com.qrbased.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;

import com.qrbased.cafe.dao.AdminRepository;
import com.qrbased.cafe.dto.Admin;
import com.qrbased.cafe.exception.AdminRegistrationException;
import com.qrbased.cafe.exception.EmailAlreadyTakenException;
import com.qrbased.cafe.security.JwtTokenProvider;
import com.qrbased.cafe.service.impl.AdminServiceImpl;
import com.qrbased.cafe.util.ApiResponse;
import com.qrbased.cafe.util.AuthResponse;
import com.qrbased.cafe.util.LoginRequest;
import com.qrbased.cafe.util.SignUpRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private AdminServiceImpl adminService;

	@PostConstruct
	public void initRoleAndUser() {
		adminService.initRoleAndUser();
	}
    
	// admin signUp
	@PostMapping("/signUpAdmin")
	public ApiResponse<SignUpRequest> registerAdmin(@RequestBody SignUpRequest signUpRequest) {

		 try {
		        if (adminRepo.existsByEmail(signUpRequest.getEmail())) {
		            throw new EmailAlreadyTakenException("Email already taken");
		        }

		        SignUpRequest registerAdmin = adminService.registerAdmin(signUpRequest);

		        return new ApiResponse<>(true, "Register Successfully", HttpStatus.CREATED.toString(),
		                HttpStatus.CREATED.value(), registerAdmin);
		    } catch (EmailAlreadyTakenException e) {
		        return new ApiResponse<>(true, e.getMessage(), HttpStatus.CONFLICT.toString(),
		                HttpStatus.CONFLICT.value(), null);
		    } catch (AdminRegistrationException e) {
		        return new ApiResponse<>(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
		                HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
		    }

	}
	
	@PostMapping("/signIn")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Get the authenticated user's email
		String userEmail = authentication.getName();
		
		Admin admin=adminRepo.findByEmail(loginRequest.getEmail()).get();
		// Generate and return the JWT token
		String token = tokenProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(token);
		authResponse.setUserName(admin.getName());
		authResponse.setUserEmail(admin.getEmail());
		authResponse.setUserRole(admin.getRoles());
		return ResponseEntity.ok(authResponse);

	}

}
