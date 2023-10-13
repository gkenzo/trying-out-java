package br.com.helloworld.todolist.errors;

public class UnableToParseUUIDError extends Exception {
  String message;

  public UnableToParseUUIDError(String message) {
    super(message);
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
