package com.chintan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.CategoryDto;
import com.chintan.dto.CategoryResponse;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.service.CategoryService;
import com.chintan.util.CommonUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-category") // @Valid
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto) {
		Boolean saveCategory = categoryService.saveCategory(categorydto);
		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("Category Saved success !", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("Category Save failed !", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}

	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);

		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception {
		try {
			CategoryDto categoryById = categoryService.getCategoryById(id);
			
			if (ObjectUtils.isEmpty(categoryById)) {
				return CommonUtil.createErrorResponse("Category not found by id: " + id, HttpStatus.NOT_FOUND);
			}
			return CommonUtil.createBuildResponse(categoryById, HttpStatus.OK);

		} catch (ResourcesNotFoundException e) {
			return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);

		}

		catch (Exception e) {
			return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) throws Exception {

		try {
			Boolean deleteCategory = categoryService.deleteCategory(id);
			if (deleteCategory) {
				return CommonUtil.createBuildResponseMessage("Category Deleted:" + id, HttpStatus.OK);
			}
			return CommonUtil.createErrorResponse("Category Delete failed !" + id, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (ResourcesNotFoundException e) {
			return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
