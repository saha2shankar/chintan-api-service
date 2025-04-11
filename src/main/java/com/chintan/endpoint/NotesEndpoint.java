package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import static com.chintan.util.Constants.DEFAULT_PAGESIZE;
import static com.chintan.util.Constants.DEFAULT_PAGENO;;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

	@PostMapping("/save-note")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception;
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	
	@GetMapping("/notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getNotes();
	
	@GetMapping("/user-notes")

	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> delteNotes(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
	
	@DeleteMapping("/empty-recycle-bin")
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception;
	
	@GetMapping("/favorite/{noteId}")
	public ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws Exception;
	
	@DeleteMapping("/unfavorite/{fevNoteId}")
	public ResponseEntity<?> UnFavoriteNote(@PathVariable Integer fevNoteId) throws Exception;
	
	@GetMapping("/favoriteNotes")
	public ResponseEntity<?> getAllFavorites();
	
	@GetMapping("/copy/{id}")
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
	
	@PostMapping("/user-search-note")
	public ResponseEntity<?> userNotesSearch(@RequestParam(name = "key", defaultValue = "")String key, @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);
	
	
	
	
	
	
}
