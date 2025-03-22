package com.chintan.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chintan.entity.AccountStatus;
import com.chintan.entity.User;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.exception.SuccessException;
import com.chintan.repository.UserRepository;
import com.chintan.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("Invalid user!"));
		
		if(user.getStatus().getVerificationCode()==null) {
			throw new SuccessException("Account already verified!");
		}
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			userRepository.save(user);
			return true;
			
		}
		return false;
	}

}
