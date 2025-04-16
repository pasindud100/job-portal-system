package com.job_platform02.job_platform02.dto;


import com.job_platform02.job_platform02.entity.UserRole;


public class UserDTO {
    private String email;
    private String password;
    private UserRole role;

    public UserDTO() {
    }

    public UserDTO(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}