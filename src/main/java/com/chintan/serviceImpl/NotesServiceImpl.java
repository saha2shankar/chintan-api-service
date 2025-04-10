package com.chintan.serviceImpl;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chintan.dto.FavoriteNoteDto;
import com.chintan.dto.NoteResponse;
import com.chintan.dto.NotesDto;
import com.chintan.dto.NotesDto.CategoryDto;
import com.chintan.dto.NotesDto.FilesDto;
import com.chintan.entity.FavoriteNote;
import com.chintan.entity.FileDetails;
import com.chintan.entity.Notes;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.repository.CategoryRepository;
import com.chintan.repository.FavoriteNoteRepository;
import com.chintan.repository.FileRepository;
import com.chintan.repository.NotesRepository;
import com.chintan.service.NotesService;
import com.chintan.util.CommonUtil;
import com.chintan.util.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private FavoriteNoteRepository  favoriteNoteRepository;

	@Autowired
	private ModelMapper mapper;
	
	
	
	@Autowired
	private Validation validation;
	
	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public Boolean saveNote(String notes, MultipartFile file) throws Exception {
		//category validation
		
		
		ObjectMapper ob =new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);
	
	if(!ObjectUtils.isEmpty(notesDto.getId())) {
		updateNotes(notesDto, file);
	}
		
		notesDto.setIsDeleted(false);
notesDto.setDeletedOn(null);
		validation.noteValidation(notesDto);
		 checkCategoryExist(notesDto.getCategory());
			 
		  Notes notesMap = mapper.map(notesDto, Notes.class); 
	    FileDetails fileDetails =	saveFileDetails(file);
	    
	    if(!ObjectUtils.isEmpty(fileDetails)) {
	    	notesMap.setFileDetails(fileDetails);
	    } else {
	    	if(ObjectUtils.isEmpty(notesDto.getId())) {
		    	notesMap.setFileDetails(null);

	    	}
	    }
		

		  Notes saveNotes =notesRepository.save(notesMap);
		  if (!ObjectUtils.isEmpty(saveNotes)) {
			  return true;
		  
		  }
		
		return false;
	}

	private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
		Notes existNotes =  notesRepository.findById(notesDto.getId()).orElseThrow(()-> new ResourcesNotFoundException("Invalid Note id"));
		if (ObjectUtils.isEmpty(file)) {
            notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), FilesDto.class));
        }
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
	if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
		String originalFilename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(originalFilename);
		List<String> extensionAllow = Arrays.asList("pdf","xlsx","jpg","docx","txt","png");
		if(!extensionAllow.contains(extension)) {
			throw new IllegalArgumentException("invalid file format ! You can only add .pdf, .xlsx, .jpg, .docs, .txt");
			
		}
		FileDetails fileDtls = new FileDetails();
		
		fileDtls.setOriginalFileName(originalFilename);
		fileDtls.setDisplayFileName(getDisplayName(originalFilename));
		String rndString = UUID.randomUUID().toString();
		String uploadfileName = rndString+"."+extension;
		fileDtls.setFileSize(file.getSize());
		fileDtls.setUploadFileName(uploadfileName);
		
		File saveFile = new File (uploadPath);
		if(!saveFile.exists()) {
			saveFile.mkdir();
		}
		
		String storePath = uploadPath.concat(uploadfileName);
		fileDtls.setPath(storePath);
		
		long upload = Files.copy(file.getInputStream(),Paths.get(storePath));
		if(upload!=0) {
		FileDetails saveFileDetails = fileRepository.save(fileDtls);
		return saveFileDetails;
		}
	}
		
		return null;
	}

	private String getDisplayName(String originalFilename) {
		// TODO Auto-generated method stub
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		if(fileName.length()>8) {
			fileName = fileName.substring(0,7);
		}
		fileName = fileName +"."+extension;
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepository.findById(category.getId()).orElseThrow(()->new ResourcesNotFoundException("Category id Invalid"));
	}

	@Override
	public List<NotesDto> getAllNotes() {

		return notesRepository.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();

	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		InputStream io = new FileInputStream(fileDetails.getPath());
		return StreamUtils.copyToByteArray(io);
	
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDetails = fileRepository.findById(id)
				.orElseThrow(()-> new ResourcesNotFoundException("File is not available !"));
		
		return fileDetails;
	}
	
	@Override
	public NoteResponse getAllNotesByUser( Integer pageNo, Integer pageSize) {
		Integer userId = CommonUtil.getLoggedInUser().getId();
	    Pageable pageable = PageRequest.of(pageNo, pageSize);
	    Page<Notes> pageNotes = notesRepository.findByCreatedBy(userId, pageable);
	    
	    List<NotesDto> notesDto = pageNotes.stream()
	        .map(n -> mapper.map(n, NotesDto.class))
	        .toList();

	    return NoteResponse.builder()
	        .notes(notesDto)
	        .pageNo(pageNotes.getNumber())
	        .pageSize(pageNotes.getSize())
	        .totalElements(pageNotes.getTotalElements())
	        .totalPages(pageNotes.getTotalPages())
	        .isFirst(pageNotes.isFirst())
	        .isLast(pageNotes.isLast())
	        .build();
	}

	@Override
	public void softDeleteNotes(Integer id) throws Exception {
		Notes notes = notesRepository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Notes Inviled or not Found!"));
		notes.setIsDeleted(true);
		notes.setDeletedOn(LocalDateTime.now());
		notesRepository.save(notes);
		
	}

	@Override
	public void restoreNotes(Integer id) throws Exception {
		Notes notes = notesRepository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Notes Inviled or not Found!"));
		notes.setIsDeleted(false);
		notes.setDeletedOn(null);
		notesRepository.save(notes);
		
		
	}

	@Override
	public List<NotesDto> getUserRecycleBinNotes() {
		Integer userId = CommonUtil.getLoggedInUser().getId();
	List<Notes> recycleBinNotes =notesRepository.findByCreatedByAndIsDeletedTrue(userId);
	List<NotesDto> noteDtoList = recycleBinNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
		
		return noteDtoList;
	}

	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
	 Notes note = notesRepository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Notes Inviled or not Found!"));
	 if(note.getIsDeleted()) {
		 notesRepository.delete(note);
	 }else {
		 throw new IllegalArgumentException("you can't delete notes dirctly !");
	 }
		
	}

	@Override
	public void emptyRecyclBin() {
		Integer userId = CommonUtil.getLoggedInUser().getId();
	List<Notes> recycleBinNote = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
	if(!CollectionUtils.isEmpty(recycleBinNote)) {
		notesRepository.deleteAll();
	}
		
	}

	@Override
	public void favoriteNotes(Integer noteId) throws Exception {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		Notes notes = notesRepository.findById(noteId).orElseThrow(()-> new ResourcesNotFoundException("Notes Not found! invalid id"));
		// TODO Auto-generated method stub
		FavoriteNote favoriteNote = FavoriteNote.builder()
				.note(notes)
				.userId(userId)
				.build();
		favoriteNoteRepository.save(favoriteNote);
	}

	@Override
	public void unFavoriteNotes(Integer favoriteNoteId) throws Exception{
		FavoriteNote favoriteNote = favoriteNoteRepository.findById(favoriteNoteId).orElseThrow(()-> new ResourcesNotFoundException("Favorite Note Not found! invalid id"));
		favoriteNoteRepository.deleteById(favoriteNoteId);
	}

	@Override
	public List<FavoriteNoteDto> getUserFavoriteNotes() {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		List<FavoriteNote> favoriteNotes = favoriteNoteRepository.findByUserId(userId);
		
		return	favoriteNotes.stream().map(fn -> mapper.map(fn, FavoriteNoteDto.class)).toList();
		 
	}

	@Override
	public Boolean copyNotes(Integer id) throws Exception {
		Notes notes = notesRepository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Notes Inviled or not Found!"));

		/*
		 * Notes copyNote = new Notes(); copyNote.setTitle(notes.getTitle());
		 */
		Notes copyNote = Notes.builder()
				.title(notes.getTitle())
				.description(notes.getDescription())
				.category(notes.getCategory())
				.isDeleted(false)
				.fileDetails(null)
				.build();
		Notes saveCopyNotes=notesRepository.save(copyNote);
		if(!ObjectUtils.isEmpty(saveCopyNotes)) {
			return true;
		}
		return false;
	}

	@Override
	public NoteResponse getSearchByUser(Integer pageNo, Integer pageSize, String  keyword) {
		Integer userId = CommonUtil.getLoggedInUser().getId();
	    Pageable pageable = PageRequest.of(pageNo, pageSize);

	    Page<Notes> pageNotes = notesRepository.searchNotes(keyword, userId, pageable);
	    
	    List<NotesDto> notesDto = pageNotes.stream()
	        .map(n -> mapper.map(n, NotesDto.class))
	        .toList();

	    return NoteResponse.builder()
	        .notes(notesDto)
	        .pageNo(pageNotes.getNumber())
	        .pageSize(pageNotes.getSize())
	        .totalElements(pageNotes.getTotalElements())
	        .totalPages(pageNotes.getTotalPages())
	        .isFirst(pageNotes.isFirst())
	        .isLast(pageNotes.isLast())
	        .build();
	}



}