package com.chintan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chintan.dto.NotesDto;
import com.chintan.entity.FileDetails;
import com.chintan.service.NotesService;
import com.chintan.util.CommonUtil;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/save-note")
	public ResponseEntity<?> saveNotes(@RequestParam String  notes, @RequestParam(required = false) MultipartFile file) throws Exception{
		Boolean saveNote = notesService.saveNote(notes, file);
		if(saveNote) {
			return CommonUtil.createBuildResponseMessage("Notes Saved Success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("something is woring save failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(data);
		
	}
	
	@GetMapping("/notes")
	public ResponseEntity<?> getNotes(){
		List<NotesDto> allNotes = notesService.getAllNotes();
		if(ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

}
