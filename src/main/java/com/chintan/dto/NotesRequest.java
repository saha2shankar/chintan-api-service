package com.chintan.dto;

import com.chintan.dto.NotesDto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesRequest {
	private String title;
	private String description;
	private CategoryDto category;

}
