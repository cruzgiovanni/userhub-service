package com.cruzgiovanni.userhub_service.controller;

import com.cruzgiovanni.userhub_service.business.UserService;
import com.cruzgiovanni.userhub_service.dto.request.UserRequestDTO;
import com.cruzgiovanni.userhub_service.dto.response.UserResponseDTO;
import com.cruzgiovanni.userhub_service.infrastructure.entities.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserRequestDTO> createUser(@RequestBody UserRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        User user  = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRequestDTO> updateUserNameById(@PathVariable Integer id, @RequestBody UserRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();
        userService.updateUserNameById(id, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<User> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

}
