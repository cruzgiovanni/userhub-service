package com.cruzgiovanni.userhub_service.business;

import com.cruzgiovanni.userhub_service.infrastructure.entities.User.User;
import com.cruzgiovanni.userhub_service.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with id " + id + " not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User with email " + email + " not found"));
    }

    public void updateUserNameById(Integer id, User user) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User with id " + id + " not found"));

        User updatedUser = User.builder()
                .id(userEntity.getId())
                .email(user.getEmail() != null ? user.getEmail() : userEntity.getEmail())
                .name(user.getName() != null ? user.getName() : userEntity.getName())
                .build();

        userRepository.saveAndFlush(updatedUser);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }
}
