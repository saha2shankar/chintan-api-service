package com.chintan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chintan.dto.FavoriteNoteDto;
import com.chintan.dto.NoteResponse;
import com.chintan.dto.NotesDto;
import com.chintan.entity.FileDetails;
import com.chintan.entity.Notes;
import com.chintan.service.NotesService;
import com.chintan.util.CommonUtil;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	@Autowired
	private NotesService notesService;

	@PostMapping("/save-note")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception {
		Boolean saveNote = notesService.saveNote(notes, file);
		if (saveNote) {
			return CommonUtil.createBuildResponseMessage("Notes Saved Success", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponseMessage("something is woring save failed",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(data);

	}

	@GetMapping("/notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getNotes() {
		List<NotesDto> allNotes = notesService.getAllNotes();
		if (ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

	@GetMapping("/user-notes")

	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize

	) {
		
		NoteResponse allNotes = notesService.getAllNotesByUser(pageNo, pageSize);

		if (ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<?> delteNotes(@PathVariable Integer id) throws Exception {
		notesService.softDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delted Success", HttpStatus.OK);

	}

	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Notes Restore success!", HttpStatus.OK);

	}

	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {
		List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		if (notes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {
		notesService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delted Success", HttpStatus.OK);

	}

	@DeleteMapping("/empty-recycle-bin")
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception {
		notesService.emptyRecyclBin();
		return CommonUtil.createBuildResponseMessage("All Notes Deleted", HttpStatus.OK);

	}

	@GetMapping("/favorite/{noteId}")
	public ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws Exception {
		notesService.favoriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage("Note added as favorite", HttpStatus.OK);

	}

	@DeleteMapping("/unfavorite/{fevNoteId}")
	public ResponseEntity<?> UnFavoriteNote(@PathVariable Integer fevNoteId) throws Exception {
		notesService.unFavoriteNotes(fevNoteId);
		return CommonUtil.createBuildResponseMessage("Note remove as favorite", HttpStatus.OK);

	}

	@GetMapping("/favoriteNotes")
	public ResponseEntity<?> getAllFavorites() {
		List<FavoriteNoteDto> userFavoriteNotes = notesService.getUserFavoriteNotes();

		if (ObjectUtils.isEmpty(userFavoriteNotes)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildResponse(userFavoriteNotes, HttpStatus.OK);
	}

	@GetMapping("/copy/{id}")
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception {
		Boolean copyNotes = notesService.copyNotes(id);
		if (copyNotes) {
			return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.OK);

		}
		return CommonUtil.createErrorResponseMessage("Copied failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
