package com.cruzgiovanni.userhub_service.controller;

import com.cruzgiovanni.userhub_service.business.UserService;
import com.cruzgiovanni.userhub_service.dto.request.UserRequestDTO;
import com.cruzgiovanni.userhub_service.dto.response.UserResponseDTO;
import com.cruzgiovanni.userhub_service.infrastructure.entitys.User;
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

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserById(@RequestParam Integer id) {
        User user  = userService.getUserById(id);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        User user  = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @PutMapping
    public ResponseEntity<UserRequestDTO> updateUserNameById(@RequestParam Integer id, @RequestBody UserRequestDTO request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .build();
        userService.updateUserNameById(id, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUserByEmail(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

}
