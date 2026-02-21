package com.cruzgiovanni.userhub_service.controller;

import com.cruzgiovanni.userhub_service.services.PostService;
import com.cruzgiovanni.userhub_service.dto.request.PostRequestDTO;
import com.cruzgiovanni.userhub_service.dto.response.PostResponseDTO;
import com.cruzgiovanni.userhub_service.infrastructure.entities.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostRequestDTO> createPost(@RequestBody PostRequestDTO request) {
        Post post = Post.builder()
                .content(request.getContent())
                .build();

        postService.createPost(post);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Integer id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(PostResponseDTO.fromEntity(post));
    }

    @GetMapping("/hub")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostResponseDTO> response = posts.stream()
                .map(PostResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Post> deletePostById(@RequestParam Integer id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
