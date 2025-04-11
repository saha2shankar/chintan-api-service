package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chintan.dto.LoginRequest;
import com.chintan.dto.UserRequest;

@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser( @RequestBody UserRequest userRequest) throws Exception ;
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser( @RequestBody LoginRequest loginRequest) throws Exception;
}
