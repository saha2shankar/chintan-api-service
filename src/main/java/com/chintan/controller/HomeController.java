package com.chintan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.service.HomeService;
import com.chintan.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	 @Autowired
	private HomeService homeService;
	

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer id, @RequestParam String vc) throws Exception{
		Boolean verifyAccount = homeService.verifyAccount(id, vc);
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
	}
}
