package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chintan.dto.PasswordResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name ="Home", description = "All the Home APIs which are not required a jwt token")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@Operation(summary = "user verification afeter register")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer id, @RequestParam String vc) throws Exception;
	
	@Operation(summary = "forget password ")
	@GetMapping("/forget-password")
	public ResponseEntity<?> senfEmailForPasswordReset(@RequestParam String email) throws Exception;

	@Operation(summary = "verify valid user for verify password ")
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer id, @RequestParam String code) throws Exception;
	
	@Operation(summary = "reset-password")
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception;
	
}
