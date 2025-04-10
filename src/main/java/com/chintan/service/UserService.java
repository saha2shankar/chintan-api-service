package com.chintan.service;

import com.chintan.dto.PasswordChangeRequest;
import com.chintan.dto.PasswordResetRequest;
import com.chintan.exception.ResourcesNotFoundException;

public interface UserService {
	
	public void changePassword(PasswordChangeRequest passwordRequest);

	public void sendEmailPasswordReset(String email) throws Exception;

	public void verifyPasswordResetLink(Integer uid, String code) throws Exception;

	public void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception;

}
