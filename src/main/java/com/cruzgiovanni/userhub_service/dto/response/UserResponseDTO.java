package com.cruzgiovanni.userhub_service.dto.response;

import com.cruzgiovanni.userhub_service.infrastructure.entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Integer id;
    private String email;
    private String name;

    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}