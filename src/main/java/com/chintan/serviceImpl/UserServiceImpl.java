package com.chintan.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chintan.config.security.CustomUserDetails;
import com.chintan.dto.EmailRequest;
import com.chintan.dto.LoginRequest;
import com.chintan.dto.LoginResponse;
import com.chintan.dto.UserDto;
import com.chintan.entity.AccountStatus;
import com.chintan.entity.Role;
import com.chintan.entity.User;
import com.chintan.repository.RoleRepository;
import com.chintan.repository.UserRepository;
import com.chintan.service.EmailService;
import com.chintan.service.UserService;
import com.chintan.util.Validation;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private Validation validation;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Override
	public Boolean register(UserDto userDto) throws Exception {

		validation.userValidation(userDto);
		
		
		User user = mapper.map(userDto, User.class);
		setRole(userDto, user);
		AccountStatus accountStatus  = AccountStatus.builder()
				
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		user.setStatus(accountStatus);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User saveUser = userRepository.save(user);
		if (!ObjectUtils.isEmpty(saveUser)) {
			emailSend(saveUser);
			return true;
		}
		return false;
	}

	private void emailSend(User saveUser) throws Exception {
		String message ="<html>"
	            + "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>"
	            + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;'>"
	            + "<h2 style='color: #4CAF50;'>Account Registration Successful</h2>"
	            + "<p>Dear <strong>" + saveUser.getFirstName() + "</strong>,</p>"
	            + "<p>Thank you for registering with us! Your account has been successfully created.</p>"
	            + "<p>To complete your registration, please verify your email address by clicking the link below:</p>"
	            + "<p style='text-align: center; margin: 20px 0;'>"
	            + "<a href='http://localhost:8080/api/v1/home/verify?id="+saveUser.getId()+"&&vc="+saveUser.getStatus().getVerificationCode()+"' style='background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Verify Your Account</a>"
	            + "</p>"
	            + "<p>If you did not create an account with us, please ignore this email.</p>"
	            + "<p>Best regards,<br><strong>Chintan App Team</strong></p>"
	            + "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>"
	            + "<p style='font-size: 12px; color: #777;'>This is an automated message. Please do not reply to this email.</p>"
	            + "</div>"
	            + "</body>"
	            + "</html>";
		//	     	message = message.replace("[[url]]", url+"api/v1/home/verify?id="+saveUser.getId()+"&&vc="+saveUser.getStatus().getVerificationCode());
		EmailRequest emailRequest = EmailRequest.builder()
				.to(saveUser.getEmail())
				.title("Account Creating Confirmation")
				.subject("Account Created Success")
				.message(message)
				.build();
		emailService.sendEmail(emailRequest);	
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepository.findAllById(reqRoleId);
		user.setRoles(roles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		if(authenticate.isAuthenticated()) {
			 CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
			 String token = "kdfssafwfejewjkfjkadskdsijwefakj";
			 LoginResponse loginResponse = LoginResponse.builder()
					 .user(mapper.map(customUserDetails.getUser(),UserDto.class))
					 .token(token)
					 .build();
			 return loginResponse;
		}
		
		return null;
	}


}
