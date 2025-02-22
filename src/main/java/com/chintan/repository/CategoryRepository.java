package com.chintan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chintan.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
