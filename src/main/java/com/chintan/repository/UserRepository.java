package com.chintan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chintan.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {


	Boolean existsByEmail(String email);

	User findByEmail(String email);

}
