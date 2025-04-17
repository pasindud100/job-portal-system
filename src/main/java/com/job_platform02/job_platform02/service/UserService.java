package com.job_platform02.job_platform02.service;

import com.job_platform02.job_platform02.dto.UserDTO;
import com.job_platform02.job_platform02.entity.User;
import com.job_platform02.job_platform02.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserDTO userDTO) {
        // Check if user already exists
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("User  already exists with this email");
        }

        User user = modelMapper.map(userDTO, User.class);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));


        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null; //User not found
        }
        return modelMapper.map(user, UserDTO.class);
    }
}