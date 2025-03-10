package com.chintan.serviceImpl;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chintan.dto.NotesDto;
import com.chintan.dto.NotesDto.CategoryDto;
import com.chintan.entity.Notes;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.repository.CategoryRepository;
import com.chintan.repository.NotesRepository;
import com.chintan.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveNote(NotesDto notesDto) throws Exception {
		//category validation
		checkCategoryExist(notesDto.getCategory());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes saveNotes = notesRepository.save(notes);
		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;

		}
		return false;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepository.findById(category.getId()).orElseThrow(()->new ResourcesNotFoundException("Category id Invalid"));
	}

	@Override
	public List<NotesDto> getAllNotes() {
		return notesRepository.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();

	}

}
