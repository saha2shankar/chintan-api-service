package com.chintan.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chintan.dto.NotesDto;

public interface NotesService {
	
	public List<NotesDto> getAllNotes();
	public Boolean saveNote(String notes, MultipartFile file) throws Exception;

}
