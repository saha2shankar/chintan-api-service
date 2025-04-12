package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chintan.dto.LoginRequest;
import com.chintan.dto.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name ="User Authentication", description = "All the user Authentication APIs")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {
	
	@Operation(summary = "User register Endpoint", tags = {"Login Screen"})
	@PostMapping("/register")
	public ResponseEntity<?> registerUser( @RequestBody UserRequest userRequest) throws Exception ;
	
	@Operation(summary = "User login Endpoint", tags = {"Login Screen"})
	@PostMapping("/login")
	public ResponseEntity<?> loginUser( @RequestBody LoginRequest loginRequest) throws Exception;
}
