package com.cruzgiovanni.userhub_service.dto.response;

import com.cruzgiovanni.userhub_service.infrastructure.entities.Post;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {
    private Integer id;
    private String content;
    private Instant createdAt;

    public static PostResponseDTO fromEntity(Post post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
