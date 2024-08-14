package com.example.todoApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todoApplication.service.TodoListService;

public class HomeController {
	
	@Autowired
	TodoListService todoListService;
	
	
	@GetMapping("/")
	public ModelAndView root() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("task" , todoListService.getAllTask() );
		return modelAndView;
	}

}
