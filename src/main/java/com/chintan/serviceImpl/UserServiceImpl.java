package com.chintan.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.chintan.dto.EmailRequest;
import com.chintan.dto.PasswordChangeRequest;
import com.chintan.dto.PasswordResetRequest;
import com.chintan.entity.User;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.repository.UserRepository;
import com.chintan.service.EmailService;
import com.chintan.service.UserService;
import com.chintan.util.CommonUtil;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

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

	@Override
	public void sendEmailPasswordReset(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourcesNotFoundException("invalid email");
		}
		//Generate Password reset token
		String passwordResetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
		User updateUser = userRepository.save(user);
	 sendEmailRequest(user);

	}
	private EmailRequest sendEmailRequest(User user) throws MessagingException, Exception {
	    String message = "<html>"
	            + "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>"
	            + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;'>"
	            + "<h2 style='color: #FF5F33E1;'>Password Reset Request</h2>"
	            + "<p>Dear <strong>" + user.getFirstName() + "</strong>,</p>"
	            + "<p>We received a request to reset your password. To proceed, please click the link below to reset your password:</p>"
	            + "<p style='text-align: center; margin: 20px 0;'>"
	            + "<a href='http://localhost:8080/api/v1/home/verify-password-link?id=" + user.getId() + "&&code=" + user.getStatus().getPasswordResetToken() + "' style='background-color: #FF5F33E1; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Reset Your Password</a>"
	            + "</p>"
	            + "<p>If you did not request a password reset, please ignore this email. Your password will remain unchanged.</p>"
	            + "<p>If you have any questions, feel free to contact our support team.</p>"
	            + "<p>Best regards,<br><strong>Chintan App Team</strong></p>"
	            + "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>"
	            + "<p style='font-size: 12px; color: #777;'>This is an automated message. Please do not reply to this email.</p>"
	            + "</div>"
	            + "</body>"
	            + "</html>";

	    EmailRequest emailRequest = EmailRequest.builder()
	            .to(user.getEmail())
	            .title("Password Reset Request")
	            .subject("Password Reset Request")
	            .message(message)
	            .build();
	    
	    emailService.sendEmail(emailRequest);
	    
	    return null;
	}

	@Override
	public void verifyPasswordResetLink(Integer uid, String code) throws Exception {
	User user =	userRepository.findById(uid).orElseThrow(()-> new ResourcesNotFoundException("Invalid User"));
		verifyPasswordResetCode(user.getStatus().getPasswordResetToken(), code);
	}

	private void verifyPasswordResetCode(String existToken, String requestToken) {
		if(StringUtils.hasText(requestToken)) {
			
			if(!StringUtils.hasText(existToken)) {
				throw new IllegalArgumentException("already password reset");
			}
			if(!existToken.equals(requestToken)) {
				throw new IllegalArgumentException("Invalid Token");

			}else {
				throw new IllegalArgumentException("verified token wait a moment.");
			}
		}else {
			throw new IllegalArgumentException("Invalid Token");
		}
		
	}

	@Override
	public void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception {
	User user =	userRepository.findById(passwordResetRequest.getUid()).orElseThrow(()-> new ResourcesNotFoundException("Invalid User"));
		String encodePassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());
		user.setPassword(encodePassword);
		user.getStatus().setPasswordResetToken(null);
		userRepository.save(user);
		
	}

}


