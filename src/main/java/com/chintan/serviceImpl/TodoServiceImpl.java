package com.chintan.serviceImpl;

import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chintan.dto.TodoDto;
import com.chintan.dto.TodoDto.StatusDto;
import com.chintan.entity.Todo;
import com.chintan.enums.TodoStatus;
import com.chintan.exception.ResourcesNotFoundException;
import com.chintan.repository.TodoRepository;
import com.chintan.service.TodoService;
import com.chintan.util.Validation;

@Service
public class TodoServiceImpl implements TodoService{

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		//validate todo status
	validation.todoValidation(todoDto);
		Todo todo = mapper.map(todoDto, Todo.class);
		todo.setStatusId(todoDto.getStatus().getId());
		Todo saveTodo = todoRepository.save(todo);
		if(ObjectUtils.isEmpty(saveTodo)) {
			return false;
		}
		return true;
	}

	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
		Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("Todo Not Found ! id Invalid"));
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		setStatus(todoDto, todo);
		return todoDto;
	}

	private void setStatus(TodoDto todoDto, Todo todo) {
		
		for(TodoStatus st: TodoStatus.values()) {
			if(st.getId().equals(todo.getStatusId())) {
				StatusDto statusDto = StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						.build();
				todoDto.setStatus(statusDto);
			}
		}
	}

	@Override
	public List<TodoDto> getTodobyUser() {
		Integer userId = 6;
	 List<Todo> todos=todoRepository.findByCreatedBy(userId);
	 return todos.stream().map(td -> mapper.map(td, TodoDto.class)).toList();
		
	}

}
