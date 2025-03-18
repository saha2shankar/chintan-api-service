package com.chintan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chintan.entity.FavoriteNote;

public interface FavoriteNoteRepository extends JpaRepository<FavoriteNote, Integer>{

List<FavoriteNote> findByUserId(Integer userId);

}
