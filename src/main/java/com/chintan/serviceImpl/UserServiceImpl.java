package com.chintan.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chintan.dto.PasswordChangeRequest;
import com.chintan.entity.User;
import com.chintan.repository.UserRepository;
import com.chintan.service.UserService;
import com.chintan.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void changePassword(PasswordChangeRequest passwordRequest) {
		User loggedInUser = CommonUtil.getLoggedInUser();
		if(!passwordEncoder.matches(passwordRequest.getOldPassword(),loggedInUser.getPassword())) {
			throw new IllegalArgumentException("Old Password Is Incorrect !!");
		}
		String encodedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
		loggedInUser.setPassword(encodedPassword);
		userRepository.save(loggedInUser);

	}
	

}
