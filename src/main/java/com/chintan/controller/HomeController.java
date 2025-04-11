package com.chintan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.PasswordResetRequest;
import com.chintan.service.HomeService;
import com.chintan.service.UserService;
import com.chintan.util.CommonUtil;


@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	Logger log = LoggerFactory.getLogger(HomeController.class);
	
	 @Autowired
	private HomeService homeService;
	 
	 @Autowired
	 private UserService userService;
	

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer id, @RequestParam String vc) throws Exception{
		log.info("HomeService : verifyUserAccount: Exceutaion Start");
		Boolean verifyAccount = homeService.verifyAccount(id, vc);
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		log.info("HomeService : verifyUserAccount: Exceutaion end");
		return CommonUtil.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("/forget-password")
	public ResponseEntity<?> senfEmailForPasswordReset(@RequestParam String email) throws Exception{
		userService.sendEmailPasswordReset(email);
		return CommonUtil.createBuildResponseMessage("Email Send Success !! check eMail and Reset your Password!", HttpStatus.OK);
	}
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer id, @RequestParam String code) throws Exception{
		userService.verifyPasswordResetLink(id, code);
		
		return CommonUtil.createBuildResponseMessage("Verifiacation Success !", HttpStatus.OK);
	}
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception{
		userService.resetPassword(passwordResetRequest);
		
		return CommonUtil.createBuildResponseMessage("Password reset success", HttpStatus.OK);
	}
	
	
}
