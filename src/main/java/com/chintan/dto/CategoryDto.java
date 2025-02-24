package com.chintan.dto;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private Integer id;
	
	//@NotBlank
	//@Max(value = 100,message = "Name is not greater then 100.")
	private String name;
	
	//@NotBlank
	// @Min(value = 10, message = "id not less then 10")
	// @Max(value = 500, message = "id not greater then 500")
	private String description;
	
	//@NotNull
	private Boolean isActive;
	
	private Integer createdBy;
	private Date createdOn;
	private Integer updateBy;
	private Date updateOn;

}
