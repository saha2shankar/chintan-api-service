package com.chintan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.PasswordResetRequest;
import com.chintan.endpoint.HomeEndpoint;
import com.chintan.service.HomeService;
import com.chintan.service.UserService;
import com.chintan.util.CommonUtil;

@RestController
public class HomeController implements HomeEndpoint {

	Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;

	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<?> verifyUserAccount( Integer id, String vc) throws Exception {
		log.info("HomeService : verifyUserAccount: Exceutaion Start");
		Boolean verifyAccount = homeService.verifyAccount(id, vc);
		if (verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		log.info("HomeService : verifyUserAccount: Exceutaion end");
		return CommonUtil.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<?> senfEmailForPasswordReset( String email) throws Exception {
		userService.sendEmailPasswordReset(email);
		return CommonUtil.createBuildResponseMessage("Email Send Success !! check eMail and Reset your Password!",
				HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> verifyPasswordResetLink( Integer id, String code)
			throws Exception {
		userService.verifyPasswordResetLink(id, code);

		return CommonUtil.createBuildResponseMessage("Verifiacation Success !", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> resetPassword( PasswordResetRequest passwordResetRequest) throws Exception {
		userService.resetPassword(passwordResetRequest);

		return CommonUtil.createBuildResponseMessage("Password reset success", HttpStatus.OK);
	}

}
