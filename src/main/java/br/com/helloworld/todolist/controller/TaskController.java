package br.com.helloworld.todolist.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.helloworld.todolist.model.TaskModel;
import br.com.helloworld.todolist.repository.ITaskRepository;
import br.com.helloworld.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

    if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date must be after end date.");
    }

    taskModel.setUserId((UUID) request.getAttribute("userId"));
    var createdTask = this.taskRepository.save(taskModel);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

  @GetMapping("/")
  public ResponseEntity listUserTasks(HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    var list = this.taskRepository.findByUserId((UUID) userId);
    return ResponseEntity.status(HttpStatus.OK).body(list);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
    var task = this.taskRepository.findById(id).orElse(null);
    var userId = request.getAttribute("userId");
    System.out.println("task  " + task);
    System.out.println("task get user id " + task.getUserId());
    System.out.println("user id from filter " + userId);
    if (!task.getUserId().equals(userId) || task == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have any task with specified ID.");
    }
    Utils.mergeObjects(taskModel, task);
    var mergedTask = this.taskRepository.save(task);
    return ResponseEntity.status(HttpStatus.OK).body(mergedTask);
  }
}
