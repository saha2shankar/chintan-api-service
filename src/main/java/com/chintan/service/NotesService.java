package com.chintan.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chintan.dto.NoteResponse;
import com.chintan.dto.NotesDto;
import com.chintan.entity.FileDetails;
import com.chintan.entity.Notes;
import com.chintan.exception.ResourcesNotFoundException;

public interface NotesService {
	
	public List<NotesDto> getAllNotes();
	public Boolean saveNote(String notes, MultipartFile file) throws Exception;
	public byte[] downloadFile(FileDetails fileDetails) throws Exception;
	public FileDetails getFileDetails(Integer id) throws Exception;
	public NoteResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);
	public void softDeleteNotes(Integer id) throws Exception;
	public void restoreNotes(Integer id) throws Exception;
	public List<NotesDto> getUserRecycleBinNotes(Integer userId);

}
