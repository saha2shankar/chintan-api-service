package com.chintan.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chintan.entity.AccountStatus;
import com.chintan.entity.User;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.exception.SuccessException;
import com.chintan.repository.UserRepository;
import com.chintan.service.HomeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("Invalid user!"));
		
		if(user.getStatus().getVerificationCode()==null) {
			log.info("Message : Account already verified!");
			throw new SuccessException("Account already verified!");
		}
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			userRepository.save(user);
			log.info("Message : Account verification Success !");
			return true;
			
		}
		return false;
	}

}
