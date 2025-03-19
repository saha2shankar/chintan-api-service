package com.chintan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chintan.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
