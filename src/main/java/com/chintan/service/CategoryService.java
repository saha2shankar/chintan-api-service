package com.chintan.service;

import java.util.List;

import com.chintan.entity.Category;

public interface CategoryService {
	public Boolean saveCategory(Category category);
	public List<Category>  getAllCategory();

}
