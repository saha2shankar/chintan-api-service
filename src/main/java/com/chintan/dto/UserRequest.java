package com.chintan.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private List<RoleDto> roles;
	private String password;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class RoleDto{
		private Integer id;
		private String name;
		
	}
}
