package com.chintan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chintan.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

}
