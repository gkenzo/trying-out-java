package br.com.helloworld.todolist.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.helloworld.todolist.controller.errors.UnableToParseUUIDError;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_task")
public class TaskModel {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private UUID userId;
  @Column(length = 24)
  private String title;
  private String description;
  @CreationTimestamp
  private LocalDateTime createdAt;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private Boolean isDone;
  private String priority;

  public void setTitle(String title) throws Exception {
    if (title.length() > 50)
      throw new Exception("Title must contain 50 characters or less");

    this.title = title;
  }

  public void setId(UUID id) throws Exception {
    if (!(id instanceof UUID))
      throw new UnableToParseUUIDError("Could not convert specified task id to a UUID");

    this.id = id;
  }
}
