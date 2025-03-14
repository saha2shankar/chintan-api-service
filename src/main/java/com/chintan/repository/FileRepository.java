package com.chintan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chintan.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer>{

}
