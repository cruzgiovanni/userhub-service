package com.cruzgiovanni.userhub_service.infrastructure.repositories;

import com.cruzgiovanni.userhub_service.infrastructure.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
