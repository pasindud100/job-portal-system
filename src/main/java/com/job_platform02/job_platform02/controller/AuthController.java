//package com.jwt.demojwt.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private com.jwt.demojwt.service.UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<com.jwt.demojwt.dto.UserDTO> register(@RequestBody com.jwt.demojwt.dto.UserDTO userDTO) {
//        com.jwt.demojwt.dto.UserDTO createdUser  = userService.registerUser (userDTO);
//        return ResponseEntity.ok(createdUser );
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody com.jwt.demojwt.dto.UserDTO userDTO) {
//        // Implement login logic here (JWT generation)
//        return ResponseEntity.ok("Login successful");
//    }
//}


package com.job_platform02.job_platform02.controller;

import com.job_platform02.job_platform02.dto.UserDTO;
import com.job_platform02.job_platform02.security.JwtUtil;
import com.job_platform02.job_platform02.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth")
public class AuthController {


    private final  UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO createdUser  = userService.registerUser(userDTO);
        return ResponseEntity.ok(createdUser );
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
//        // Authenticate the user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
//        );
//
//        // If authentication is successful, generate JWT token
//        UserDTO user = userService.findUserByEmail(userDTO.getEmail());
//        String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
//        return ResponseEntity.ok(jwtToken);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
//            );
//
//            // If authentication is successful, generate JWT token
//            UserDTO user = userService.findUserByEmail(userDTO.getEmail());
//            if (user == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//            }
//            String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
//            return ResponseEntity.ok(jwtToken);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login failed: " + e.getMessage());
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        try {
            // Authenticate the user...
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

            // If authentication is successful, generate jwt token
            UserDTO user = userService.findUserByEmail(userDTO.getEmail());
            String jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

            // Create a response object
            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", jwtToken);
            response.put("role", user.getRole().name()); // Include the user role

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}