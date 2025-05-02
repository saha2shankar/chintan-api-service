package com.chintan.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.chintan.dto.CategoryDto;
import com.chintan.dto.NotesDto;
import com.chintan.dto.TodoDto;
import com.chintan.dto.TodoDto.StatusDto;
import com.chintan.dto.UserRequest;
import com.chintan.enums.TodoStatus;
import com.chintan.exception.ExistDataException;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.repository.RoleRepository;
import com.chintan.repository.UserRepository;

@Component
public class Validation {
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
    
    public void categoryValidation(CategoryDto categoryDto) {
        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("Category Object shouldn't be null or empty");
        }

        Map<String, Object> error = new LinkedHashMap<>();

        // Validate Name
        String name = categoryDto.getName();
        if (ObjectUtils.isEmpty(name)) {
            error.put("name", "Name field is empty or null");
        } else if (name.length() < 3) {
            error.put("name", "Name length must be at least 3 characters");
        } else if (name.length() > 50) {
            error.put("name", "Name length must be less than 50 characters");
        }

        // Validate Description
        String description = categoryDto.getDescription();
        if (ObjectUtils.isEmpty(description)) {
            error.put("description", "Description field is empty or null");
        } else if (description.length() < 10) {
            error.put("description", "Description length must be at least 10 characters");
        } else if (description.length() > 500) {
            error.put("description", "Description length must be less than 500 characters");
        }
        if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
            error.put("isActive", "IsActive field is empty or null");
        }else {
        	 if (!(categoryDto.getIsActive() instanceof Boolean)) {
        	    error.put("isActive", "IsActive field must be a boolean value (true or false)");
        	}

        }
        
        // If there are errors, throw an exception with the error details
        if (!error.isEmpty()) {
            throw new  ValidationException(error);
        }
    }
    public void noteValidation(NotesDto notesDto) {
    	 if (ObjectUtils.isEmpty(notesDto)) {
             throw new IllegalArgumentException("Category Object shouldn't be null or empty");
         }

         Map<String, Object> error = new LinkedHashMap<>();
         
      // Validate Name
         String title = notesDto.getTitle();
         if (ObjectUtils.isEmpty(title)) {
             error.put("titel", "title field is empty or null");
         } else if (title.length() < 3) {
             error.put("titel", "Title length must be at least 3 characters");
         } else if (title.length() > 100) {
             error.put("title", "Name length must be less than 100 characters");
         }
         

         // Validate Description
         String description = notesDto.getDescription();
         if (ObjectUtils.isEmpty(description)) {
             error.put("description", "Description field is empty or null");
         } else if (description.length() < 10) {
             error.put("description", "Description length must be at least 10 characters");
         } else if (description.length() > 500) {
             error.put("description", "Description length must be less than 1000 characters");
         }
         

         
         // If there are errors, throw an exception with the error details
         if (!error.isEmpty()) {
             throw new  ValidationException(error);
         }
    }
    public void todoValidation(TodoDto todo) throws Exception {
    	StatusDto reqStatus = todo.getStatus();
    	Boolean statusFound = false;
    	for(TodoStatus st: TodoStatus.values()) {
    		if(st.getId().equals(reqStatus.getId())) {
    			statusFound = true;
    		}
    	}
    	if(!statusFound) {
    		throw new ResourcesNotFoundException("Invalid Status");
    	}
    }
    
	public void userValidation(UserRequest userRequest) {

		if (!StringUtils.hasText(userRequest.getFirstName())) {
			throw new IllegalArgumentException("first name is invalid");
		}

		if (!StringUtils.hasText(userRequest.getLastName())) {
			throw new IllegalArgumentException("last name is invalid");
		}

		if (!StringUtils.hasText(userRequest.getEmail()) || !userRequest.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("email is invalid");
		}else {
			// email exits
			Boolean existEmail = userRepository.existsByEmail(userRequest.getEmail());
			if(existEmail) {
				throw new ExistDataException("Email already exists !");
			}
			
		}

		if (!StringUtils.hasText(userRequest.getMobileNo()) || !userRequest.getMobileNo().matches(Constants.MOBILE_REGEX)) {
			throw new IllegalArgumentException("mobno is invalid");
		}

//		if (CollectionUtils.isEmpty(userRequest.getRoles())) {
//			throw new IllegalArgumentException("role is invalid");
//		} else {
//
//			List<Integer> roleIds = roleRepository.findAll().stream().map(r -> r.getId()).toList();
//
//			List<Integer> invalidReqRoleids = userRequest.getRoles().stream().map(r -> r.getId())
//					.filter(roleId -> !roleIds.contains(roleId)).toList();
//
//			if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
//				throw new IllegalArgumentException("role is invalid" + invalidReqRoleids);
//			}
//
//		}

	}
	
	
	
	

}