package com.chintan.service;

import java.util.List;


import com.chintan.dto.CategoryDto;
import com.chintan.dto.CategoryResponse;

public interface CategoryService {
	public Boolean saveCategory(CategoryDto categoryDto);
	public List<CategoryDto>  getAllCategory();
	public List<CategoryResponse>  getActiveCategory();
	public  CategoryDto getCategoryById(Integer id);
	public  Boolean deleteCategory(Integer id);



}
