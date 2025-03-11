package com.chintan.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.chintan.dto.CategoryDto;
import com.chintan.dto.NotesDto;

@Component
public class Validation {
    
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
    
}
