package com.example.todoApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoApplication.model.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

}
