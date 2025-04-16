package com.job_platform02.job_platform02.service;

/* Author : pasindu
 place: ACPT student*/

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
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null; // Base case to stop recursion
        }
        return modelMapper.map(user, UserDTO.class);
    }
}