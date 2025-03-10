package com.chintan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.NotesDto;
import com.chintan.service.NotesService;
import com.chintan.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/save-note")
	public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws Exception{
		Boolean saveNote = notesService.saveNote(notesDto);
		if(!ObjectUtils.isEmpty(saveNote)) {
			return CommonUtil.createBuildResponseMessage("Notes Saved Success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("something is woring save failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}
	
	@GetMapping("/notes")
	public ResponseEntity<?> getNotes(@RequestBody NotesDto notesDto){
		List<NotesDto> allNotes = notesService.getAllNotes();
		if(ObjectUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}

}
