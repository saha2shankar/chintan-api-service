package com.chintan.serviceImpl;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.chintan.dto.CategoryDto;
import com.chintan.dto.CategoryResponse;
import com.chintan.entity.Category;
import com.chintan.exception.ExistDataException;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.repository.CategoryRepository;
import com.chintan.service.CategoryService;
import com.chintan.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;

	@Override
	public Boolean saveCategory(CategoryDto categorydto) {
		//Validation checking
		validation.categoryValidation(categorydto);
		//check exist or not
		Boolean exist = categoryRepository.existsByName(categorydto.getName().trim());
		if(exist) {
			//throw error
			throw new ExistDataException("Category Already Exist");
		}

		Category category = mapper.map(categorydto, Category.class);
		if (ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
		//	category.setCreatedBy(1); 
		//	category.setCreatedOn(new Date());
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
			//category.setUpdateBy(1);
			//category.setUpdateOn(new Date());
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
	public CategoryDto getCategoryById(Integer id) throws Exception {
	    // Fetch the category from the repository, throw an exception if not found
	    Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
	            .orElseThrow(() -> new ResourcesNotFoundException("Category not found with id = " + id));

	    // Map the category entity to a CategoryDto and return it
	    return mapper.map(category, CategoryDto.class);
	}

	@Override
	public Boolean deleteCategory(Integer id) throws Exception {
	    // Fetch the category from the repository, throw an exception if not found
	    Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
	            .orElseThrow(() -> new ResourcesNotFoundException("Category not found with id: " + id));

	    // Soft delete the category by setting isDeleted to true
	    category.setIsDeleted(true);
	    categoryRepository.save(category);

	    // Return true to indicate successful deletion
	    return true;
	}
}
