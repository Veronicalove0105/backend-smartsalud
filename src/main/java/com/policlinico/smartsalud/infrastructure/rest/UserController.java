package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.UserUseCase;
import com.policlinico.smartsalud.application.dto.UsuarioDTO;
import com.policlinico.smartsalud.application.dto.UsuarioRequest;
import com.policlinico.smartsalud.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User request) {
        User saved = userUseCase.registerUser(
                new User(null, request.getUsername(), request.getEmail(),
                        request.getPassword(), request.getRole(), true));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userUseCase.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userUseCase.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User request) {
        return ResponseEntity.ok(userUseCase.updateUser(id,
                new User(null, request.getUsername(), request.getEmail(),
                        request.getPassword(), request.getRole(), request.isActive())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}