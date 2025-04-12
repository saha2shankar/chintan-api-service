package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chintan.dto.CategoryDto;
import com.chintan.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.chintan.util.Constants.ROLE_ADMIN;
import static com.chintan.util.Constants.ROLE_ADMIN_USER;

@Tag(name ="Category", description = "All the Category operations APIs")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {
	
	@Operation(summary = "save category")
	@PostMapping("/save-category") // @Valid
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto);
	
	@Operation(summary = "get all the category")
	@GetMapping("/categories")     
	@PreAuthorize(Constants.ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	@Operation(summary = "get all active category")
	@GetMapping("/active-category")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	@Operation(summary = "get category by Id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "delete categoty")
	@PreAuthorize(ROLE_ADMIN)
	@DeleteMapping("/delete-category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) throws Exception;
	
	
}
