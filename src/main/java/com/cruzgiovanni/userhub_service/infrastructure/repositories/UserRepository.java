package com.cruzgiovanni.userhub_service.infrastructure.repositories;

import com.cruzgiovanni.userhub_service.infrastructure.entities.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByLogin(String login);

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteUserByEmail(String email);
}
