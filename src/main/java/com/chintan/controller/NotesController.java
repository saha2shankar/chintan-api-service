package com.chintan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chintan.dto.FavoriteNoteDto;
import com.chintan.dto.NoteResponse;
import com.chintan.dto.NotesDto;
import com.chintan.endpoint.NotesEndpoint;
import com.chintan.entity.FileDetails;
import com.chintan.service.NotesService;
import com.chintan.util.CommonUtil;

@RestController
public class NotesController implements NotesEndpoint {
	@Autowired
	private NotesService notesService;

	@Override
	public ResponseEntity<?> saveNotes( String notes, MultipartFile file)
			throws Exception {
		Boolean saveNote = notesService.saveNote(notes, file);
		if (saveNote) {
			return CommonUtil.createBuildResponseMessage("Notes Saved Success", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("something is woring save failed",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> downloadFile( Integer id) throws Exception {
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(data);

	}

	@Override
	public ResponseEntity<?> getNotes() {
		List<NotesDto> allNotes = notesService.getAllNotes();
		if (ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAllNotesByUser(Integer pageNo,Integer pageSize) {

		NoteResponse allNotes = notesService.getAllNotesByUser(pageNo, pageSize);

		if (ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> delteNotes(Integer id) throws Exception {
		notesService.softDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delted Success", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> restoreNotes(Integer id) throws Exception {
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Notes Restore success!", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {
		List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		if (notes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> hardDeleteNotes( Integer id) throws Exception {
		notesService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delted Success", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception {
		notesService.emptyRecyclBin();
		return CommonUtil.createBuildResponseMessage("All Notes Deleted", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> favoriteNote( Integer noteId) throws Exception {
		notesService.favoriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage("Note added as favorite", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> UnFavoriteNote( Integer fevNoteId) throws Exception {
		notesService.unFavoriteNotes(fevNoteId);
		return CommonUtil.createBuildResponseMessage("Note remove as favorite", HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> getAllFavorites() {
		List<FavoriteNoteDto> userFavoriteNotes = notesService.getUserFavoriteNotes();

		if (ObjectUtils.isEmpty(userFavoriteNotes)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(userFavoriteNotes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> copyNotes(Integer id) throws Exception {
		Boolean copyNotes = notesService.copyNotes(id);
		if (copyNotes) {
			return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.OK);

		}
		return CommonUtil.createErrorResponseMessage("Copied failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<?> userNotesSearch(String key, Integer pageNo, Integer pageSize) {

		NoteResponse allNotes = notesService.getSearchByUser(pageNo, pageSize, key);

		if (ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

}
