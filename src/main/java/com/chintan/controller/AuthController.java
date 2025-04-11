package com.chintan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.LoginRequest;
import com.chintan.dto.LoginResponse;
import com.chintan.dto.UserRequest;
import com.chintan.service.AuthService;
import com.chintan.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	private ResponseEntity<?> registerUser( @RequestBody UserRequest userRequest) throws Exception{
		log.info("AuthController : registerUser() : Exceution Start");
		Boolean register = authService.register(userRequest);
		if(!register) {
			
			log.info("Error : {}","Register failed");
			return CommonUtil.createErrorResponseMessage("Register Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		log.info("AuthController : registerUser() : Exceution End");
		return CommonUtil.createBuildResponseMessage("Register Successfully", HttpStatus.CREATED);

	
	}
	
	@PostMapping("/login")
	private ResponseEntity<?> loginUser( @RequestBody LoginRequest loginRequest) throws Exception{
		
		LoginResponse loginResponse = authService.login(loginRequest);
		if(ObjectUtils.isEmpty(loginResponse)) {
			return CommonUtil.createErrorResponseMessage("invalid Credential", HttpStatus.BAD_REQUEST);
		}
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
		}

}
