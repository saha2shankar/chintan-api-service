package com.chintan.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.PasswordChangeRequest;
import com.chintan.dto.UserResponse;
import com.chintan.endpoint.UserEndpoint;
import com.chintan.entity.User;
import com.chintan.service.UserService;
import com.chintan.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController implements UserEndpoint {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> getProfile(){
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?> ChangePassword(PasswordChangeRequest passwordChangeRequest){
		userService.changePassword(passwordChangeRequest);
		return CommonUtil.createBuildResponseMessage("Password changed Success !!", HttpStatus.OK);
	}

}
