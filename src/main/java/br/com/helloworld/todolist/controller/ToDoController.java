package br.com.helloworld.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ToDoController {
  @GetMapping
  public String GetToDos() {
    return "hello world";
  }
}
