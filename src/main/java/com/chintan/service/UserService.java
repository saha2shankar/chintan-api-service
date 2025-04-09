package com.chintan.service;

import com.chintan.dto.PasswordChangeRequest;

public interface UserService {
	
	public void changePassword(PasswordChangeRequest passwordRequest);

}
