package com.cruzgiovanni.userhub_service.dto.request;

import com.cruzgiovanni.userhub_service.infrastructure.entities.User.UserRole;

public record RegisterDTO(
        String login,
        String email,
        String name,
        String password,
        UserRole role) {
}
