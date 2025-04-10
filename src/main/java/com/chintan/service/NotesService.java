package com.chintan.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chintan.dto.FavoriteNoteDto;
import com.chintan.dto.NoteResponse;
import com.chintan.dto.NotesDto;
import com.chintan.entity.FileDetails;


public interface NotesService {
	
	public List<NotesDto> getAllNotes();
	public Boolean saveNote(String notes, MultipartFile file) throws Exception;
	public byte[] downloadFile(FileDetails fileDetails) throws Exception;
	public FileDetails getFileDetails(Integer id) throws Exception;
	public NoteResponse getAllNotesByUser(Integer pageNo, Integer pageSize);
	public void softDeleteNotes(Integer id) throws Exception;
	public void restoreNotes(Integer id) throws Exception;
	public List<NotesDto> getUserRecycleBinNotes();
	public void hardDeleteNotes(Integer id) throws Exception;
	public void emptyRecyclBin();
	public void favoriteNotes(Integer noteId) throws Exception;
	public void unFavoriteNotes(Integer noteId) throws Exception;
	public List<FavoriteNoteDto> getUserFavoriteNotes();
	public Boolean copyNotes(Integer id) throws Exception;
	public NoteResponse getSearchByUser(Integer pageNo, Integer pageSize, String keyword);

}
