package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chintan.dto.PasswordResetRequest;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer id, @RequestParam String vc) throws Exception;
	
	@GetMapping("/forget-password")
	public ResponseEntity<?> senfEmailForPasswordReset(@RequestParam String email) throws Exception;
	
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer id, @RequestParam String code) throws Exception;
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception;
	
}
