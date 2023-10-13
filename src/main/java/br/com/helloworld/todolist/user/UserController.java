package br.com.helloworld.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController

@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel userModel) {
    UserModel usernameAlreadyTaken = this.userRepository.findByUsername(userModel.getUsername());

    if (usernameAlreadyTaken != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Error("Credencial inválida. Refaça seu username ou senha").getMessage());
    }

    String hashedPassword = BCrypt.withDefaults().hashToString(6, userModel.getPassword().toCharArray());
    userModel.setPassword(hashedPassword);
    var createdUser = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }
}
