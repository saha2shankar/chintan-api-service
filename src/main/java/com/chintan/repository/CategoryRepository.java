package com.chintan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chintan.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Optional<Category> findByIdAndIsDeletedFalse(Integer id);

	List<Category> findByIsActiveTrueAndIsDeletedFalse();

	List<Category> findByAndIsDeletedFalse();

	Boolean existsByName(String name);


}
