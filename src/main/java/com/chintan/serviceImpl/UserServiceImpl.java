package com.chintan.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chintan.dto.UserDto;
import com.chintan.entity.Role;
import com.chintan.entity.User;
import com.chintan.repository.RoleRepository;
import com.chintan.repository.UserRepository;
import com.chintan.service.UserService;
import com.chintan.util.Validation;

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

	@Override
	public Boolean register(UserDto userDto) {

		validation.userValidation(userDto);
		
		
		User user = mapper.map(userDto, User.class);
		
		

		setRole(userDto, user);

		User saveUser = userRepository.save(user);
		if (!ObjectUtils.isEmpty(saveUser)) {
			return true;
		}
		return false;
	}

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepository.findAllById(reqRoleId);
		user.setRoles(roles);
	}


}
