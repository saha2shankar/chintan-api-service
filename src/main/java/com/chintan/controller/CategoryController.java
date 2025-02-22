package com.chintan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.CategoryDto;
import com.chintan.dto.CategoryResponse;
import com.chintan.entity.Category;
import com.chintan.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto){
		Boolean saveCategory = categoryService.saveCategory(categorydto);
		if(saveCategory) {
			return new ResponseEntity<>("saved", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("not saved ", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
	}else {
		return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}
}
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
	}else {
		return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}
}
}
