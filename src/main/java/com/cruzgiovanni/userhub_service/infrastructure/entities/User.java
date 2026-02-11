package com.cruzgiovanni.userhub_service.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;
}
