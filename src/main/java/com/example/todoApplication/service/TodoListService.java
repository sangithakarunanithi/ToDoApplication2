package com.example.todoApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todoApplication.model.TodoList;
import com.example.todoApplication.repository.TodoListRepository;

@Service
public class TodoListService {
	
	@Autowired
	TodoListRepository todoListRepository;
	
	
	public List<TodoList> getAllTask(){
		return todoListRepository.findAll();
	}


	public List<TodoList> addTask(List<TodoList> todoList) {
		List<TodoList> result  = new ArrayList<TodoList>();
		for(TodoList eachlist : todoList) {
			TodoList list = new TodoList();
			list.setDescription(eachlist.getDescription());
			list.setStatus(eachlist.isStatus());
			result.add(todoListRepository.save(list));
		}
		return result;	
	}


	public boolean deleteTask(Long id) {
		if (todoListRepository.existsById(id)) {
			todoListRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
	}


	public void editTask(Long id, TodoList todoList) {
		TodoList list = todoListRepository.getReferenceById(id);
		list.setStatus(todoList.isStatus());
		todoListRepository.save(list);
      
	}
	
	

}
