package com.chintan.service;

import java.util.List;

import com.chintan.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNote(NotesDto notesDto);
	public List<NotesDto> getAllNotes();

}
