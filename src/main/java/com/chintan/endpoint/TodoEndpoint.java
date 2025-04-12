package com.chintan.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chintan.dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo Management", description = "Endpoints for managing TODO tasks")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @Operation(
        summary = "Save Todo",
        description = "Save a new Todo item for the logged-in user."
    )
    @PostMapping("/save")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;

    @Operation(
        summary = "Get Todo by ID",
        description = "Retrieve a Todo item by its ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;

    @Operation(
        summary = "Get All Todos by User",
        description = "Retrieve all Todo items for the currently logged-in user."
    )
    @GetMapping("/list")
    public ResponseEntity<?> getAllTodoByUser();
}
