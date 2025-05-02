package com.chintan.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.CategoryDto;
import com.chintan.dto.CategoryResponse;
import com.chintan.endpoint.CategoryEndpoint;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.service.CategoryService;
import com.chintan.util.CommonUtil;

@RestController
public class CategoryController implements CategoryEndpoint {
	@Autowired
	private CategoryService categoryService;

	
	@Override
	public ResponseEntity<?> saveCategory(CategoryDto categorydto) {
		Boolean saveCategory = categoryService.saveCategory(categorydto);
		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("Category Saved success !", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("Category Save failed !", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	@Override
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}

	
	@Override
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();

		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);

		}
	}

	@Override
	public ResponseEntity<?> getCategoryById(Integer id) throws Exception {
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
	@Override
	public ResponseEntity<?> deleteCategory( Integer id) throws Exception {

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
