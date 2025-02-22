package com.chintan.serviceImpl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chintan.dto.CategoryDto;
import com.chintan.dto.CategoryResponse;
import com.chintan.entity.Category;
import com.chintan.repository.CategoryRepository;
import com.chintan.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveCategory(CategoryDto categorydto) {
//		
//		Category category  = new Category();
//		category.setName(categorydto.getName());
//		category.setDescription(categorydto.getDescription());
//		category.setIsActive(categorydto.getIsActive());
//		
		Category category = mapper.map(categorydto, Category.class);
 		category.setIsDeleted(false);
 		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		
		
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoryDtoList = categories.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepository.findByIsActiveTrue();
		List<CategoryResponse> categoryList = categories.stream().map(category -> mapper.map(category, CategoryResponse.class)).toList();
		return categoryList;
	}

}
