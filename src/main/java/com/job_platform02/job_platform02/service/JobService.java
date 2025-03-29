package com.job_platform02.job_platform02.service;


import com.job_platform02.job_platform02.dto.JobDTO;
import com.job_platform02.job_platform02.entity.Job;
import com.job_platform02.job_platform02.entity.User;
import com.job_platform02.job_platform02.repository.JobRepository;
import com.job_platform02.job_platform02.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public JobService(JobRepository jobRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public JobDTO createJob(JobDTO jobDTO) {
        User employer = userRepository.findById(jobDTO.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Job job = modelMapper.map(jobDTO, Job.class);
        job.setEmployer(employer);
        job.setCreatedAt(LocalDateTime.now());

        Job savedJob = jobRepository.save(job);
        return modelMapper.map(savedJob, JobDTO.class);
    }

    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return modelMapper.map(job, JobDTO.class);
    }

    public List<JobDTO> getJobsByCategory(String category) {
        List<Job> jobs = jobRepository.findByCategory(category);
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existingJob.setTitle(jobDTO.getTitle());
        existingJob.setDescription(jobDTO.getDescription());
        existingJob.setCategory(jobDTO.getCategory());
        existingJob.setCompany(jobDTO.getCompany());
        existingJob.setLocation(jobDTO.getLocation());

        Job updatedJob = jobRepository.save(existingJob);
        return modelMapper.map(updatedJob, JobDTO.class);
    }

    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found");
        }
        jobRepository.deleteById(id);
    }
}

