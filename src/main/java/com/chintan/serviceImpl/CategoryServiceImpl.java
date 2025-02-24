package com.chintan.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

		Category category = mapper.map(categorydto, Category.class);
		if (ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
			category.setCreatedBy(1);
			category.setCreatedOn(new Date());
		}else {
			updateCategory(category);
		}

		
		Category saveCategory = categoryRepository.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}

		return true;
	}

	private void updateCategory(Category category) {
		
		Optional<Category> findById = categoryRepository.findById(category.getId());
		if(findById.isPresent()) {
			Category existcategory = findById.get();
			category.setCreatedBy(existcategory.getCreatedBy());
			category.setCreatedOn(existcategory.getCreatedOn());
			category.setIsDeleted(existcategory.getIsDeleted());
			category.setUpdateBy(1);
			category.setUpdateOn(new Date());
		}
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findByAndIsDeletedFalse();
		List<CategoryDto> categoryDtoList = categories.stream().map(category -> mapper.map(category, CategoryDto.class))
				.toList();
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream()
				.map(category -> mapper.map(category, CategoryResponse.class)).toList();
		return categoryList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> findCategoryById = categoryRepository.findByIdAndIsDeletedFalse(id);

		if (findCategoryById.isPresent()) {
			Category category = findCategoryById.get();
			return mapper.map(category, CategoryDto.class);
		}

		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> findCategoryById = categoryRepository.findByIdAndIsDeletedFalse(id);

		if (findCategoryById.isPresent()) {
			Category category = findCategoryById.get();
			category.setIsDeleted(true);
			categoryRepository.save(category);
			return true;
		}

		return false;
	}
}
