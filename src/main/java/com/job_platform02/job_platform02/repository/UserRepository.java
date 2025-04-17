package com.job_platform02.job_platform02.repository;

import com.job_platform02.job_platform02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}