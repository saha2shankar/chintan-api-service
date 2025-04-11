package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chintan.dto.TodoDto;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	@PostMapping("/save")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTodoById( @PathVariable Integer id) throws Exception;
	
	@GetMapping("/list")
	public ResponseEntity<?> getAllTodoByUser();
}
