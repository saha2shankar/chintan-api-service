package com.chintan.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chintan.entity.Notes;
import com.chintan.repository.NotesRepository;

@Component
public class NotesSchedular {
	
	@Autowired
	private NotesRepository notesRepository;
	
	@Scheduled(cron = "0 0 0 * * ?")
	//@Scheduled(cron = "0 0/1 * * * * ")
	public void deleteNotesSchdular() {
	LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
	List<Notes> deletdNotes =	notesRepository.findAllByIsDeletedAndDeletedOnBefore(true,cutOffDate);
	notesRepository.deleteAll(deletdNotes);
	System.out.println("it's 12: 00 am i want to clean my database !! ha ha ha ");
	
	}
	

}
