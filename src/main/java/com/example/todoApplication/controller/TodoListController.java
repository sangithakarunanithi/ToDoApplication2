package com.example.todoApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoApplication.model.TodoList;
import com.example.todoApplication.service.TodoListService;

@RestController
public class TodoListController {

	    @Autowired
	    private TodoListService todoListService;

	    @PostMapping(value = "/addTask", consumes = "application/json")
	    public String addTodoList(@RequestBody List<TodoList> task) {
	    	todoListService.addTask(task);
	        return "redirect:/";
	    }

	    @GetMapping("/getAllTask")
	    public ResponseEntity<List<TodoList>> getAllTasks() {
	        List<TodoList> tasks = todoListService.getAllTask();
	        return ResponseEntity.ok(tasks);
	    }

	    @DeleteMapping("/deleteTask/{id}")
	    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
	    	if(todoListService.deleteTask(id)) {
	    		return  ResponseEntity.ok().build();
	    	}else {
	    		return ResponseEntity.notFound().build();
	    	} 
	    }
	    
	    @PutMapping("/editTask/{id}")
	    public ResponseEntity<Void> editTask(@PathVariable Long id, @RequestBody TodoList todoList) {
	        todoListService.editTask(id,todoList);
	        return ResponseEntity.ok().build();
	    }
	    
}
