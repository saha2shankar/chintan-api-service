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

import com.chintan.dto.NotesDto;
import com.chintan.dto.NotesRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.chintan.util.Constants.DEFAULT_PAGESIZE;
import static com.chintan.util.Constants.DEFAULT_PAGENO;

@Tag(name = "Notes Notes Screen", description = "All the Notes APIs which require a JWT token")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @Operation(summary = "Save Note", description = "Save a note with optional file attachment.")
    @PostMapping(value ="/save-note", consumes ="multipart/form-date")
    public ResponseEntity<?> saveNotes(@RequestParam
    		@Parameter(description = "Json String Notes", required = true,
    		content = @Content(schema = @Schema(implementation = NotesRequest.class))
    				) String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @Operation(summary = "Download File", description = "Download the attached file from the note by its ID.")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get Notes", description = "Retrieve all active notes for the current user.")
    @GetMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getNotes();

    @Operation(summary = "Get All Notes by User (Paginated)", description = "Retrieve paginated notes created by the logged-in user.")
    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);

    @Operation(summary = "Soft Delete Note", description = "Soft delete the note by moving it to recycle bin.")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Hard Delete Note", description = "Permanently delete the note by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Restore Note", description = "Restore a soft-deleted note from the recycle bin.")
    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get Recycle Bin Notes", description = "Get all notes in the user's recycle bin.")
    @GetMapping("/recycle-bin")
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

    @Operation(summary = "Empty Recycle Bin", description = "Permanently delete all notes from the user's recycle bin.")
    @DeleteMapping("/empty-recycle-bin")
    public ResponseEntity<?> emptyUserRecycleBin() throws Exception;

    @Operation(summary = "Mark as Favorite", description = "Mark a note as favorite by its ID.")
    @GetMapping("/favorite/{noteId}")
    public ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws Exception;

    @Operation(summary = "Unmark Favorite", description = "Remove a note from favorites by its favorite note ID.")
    @DeleteMapping("/unfavorite/{fevNoteId}")
    public ResponseEntity<?> UnFavoriteNote(@PathVariable Integer fevNoteId) throws Exception;

    @Operation(summary = "Get All Favorite Notes", description = "Retrieve all notes marked as favorites.")
    @GetMapping("/favoriteNotes")
    public ResponseEntity<?> getAllFavorites();

    @Operation(summary = "Copy Note", description = "Duplicate a note by its ID.")
    @GetMapping("/copy/{id}")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Search Notes by User", description = "Search user notes with keyword and pagination.")
    @PostMapping("/user-search-note")
    public ResponseEntity<?> userNotesSearch(
            @RequestParam(name = "key", defaultValue = "") String key,
            @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);
}
