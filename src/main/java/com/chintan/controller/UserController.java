package com.chintan.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.PasswordChangeRequest;
import com.chintan.dto.UserResponse;
import com.chintan.entity.User;
import com.chintan.service.UserService;
import com.chintan.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	
	@PostMapping("/change-password")
	public ResponseEntity<?> ChangePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
		userService.changePassword(passwordChangeRequest);
		return CommonUtil.createBuildResponseMessage("Password changed Success !!", HttpStatus.OK);
	}

}
