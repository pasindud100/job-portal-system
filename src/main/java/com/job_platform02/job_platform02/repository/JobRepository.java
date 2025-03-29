package com.job_platform02.job_platform02.repository;


import com.job_platform02.job_platform02.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCategory(String category);
}
