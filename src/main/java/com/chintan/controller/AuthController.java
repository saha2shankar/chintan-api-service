package com.chintan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.UserDto;
import com.chintan.service.UserService;
import com.chintan.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	private ResponseEntity<?> registerUser( @RequestBody UserDto userDto) throws Exception{
		Boolean register = userService.register(userDto);
		if(register) {
			return CommonUtil.createBuildResponseMessage("Register Successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Register Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
