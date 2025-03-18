package com.chintan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chintan.dto.TodoDto;
import com.chintan.service.TodoService;
import com.chintan.util.CommonUtil;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception{
		
		Boolean saveTodo = todoService.saveTodo(todo);
		if(saveTodo) {
			return CommonUtil.createBuildResponseMessage("Todo Saved Success !", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Todo save failed !", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTodoById( @PathVariable Integer id) throws Exception{
		
		TodoDto todoById = todoService.getTodoById(id);
		if(ObjectUtils.isEmpty(todoById)) {
			return CommonUtil.createErrorResponseMessage("Something Woring!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return CommonUtil.createBuildResponse(todoById, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<?> getAllTodoByUser(){
		
		List<TodoDto> todoList = todoService.getTodobyUser();
		if(CollectionUtils.isEmpty(todoList)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
	}


}
