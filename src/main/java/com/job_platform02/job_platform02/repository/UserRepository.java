package com.job_platform02.job_platform02.repository;

/* Author : pasindu
 place: ACPT student*/

import com.job_platform02.job_platform02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
