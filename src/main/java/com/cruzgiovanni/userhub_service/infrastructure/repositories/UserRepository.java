package com.cruzgiovanni.userhub_service.infrastructure.repositories;

import com.cruzgiovanni.userhub_service.infrastructure.entitys.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteUserByEmail(String email);
}
