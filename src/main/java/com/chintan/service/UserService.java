package com.chintan.service;

import com.chintan.dto.LoginRequest;
import com.chintan.dto.LoginResponse;
import com.chintan.dto.UserRequest;

public interface UserService {

	public Boolean register(UserRequest userRequest) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);
}
