package com.cruzgiovanni.userhub_service.services;

import com.cruzgiovanni.userhub_service.infrastructure.entities.Post;
import com.cruzgiovanni.userhub_service.infrastructure.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(Post post) {
        postRepository.saveAndFlush(post);
    }

    public Post getPostById (Integer id) {
        return postRepository.findById(Long.valueOf(id)).orElseThrow(() ->
                new RuntimeException("Post not found"));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void deletePostById(Integer id) {
        postRepository.deleteById(Long.valueOf(id));
    }

}
