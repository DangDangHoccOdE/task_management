package com.example.controller;

import com.example.config.JwtProvider;
import com.example.config.resquest.LoginRequest;
import com.example.model.User;
import com.example.model.dto.UserDto;
import com.example.repository.UserRepository;
import com.example.response.AuthResponse;
import com.example.service.CustomUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserServiceImplementation customUserServiceImplementation;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@Validated @RequestBody UserDto user) throws Exception {
        String email = user.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        String fullName = user.getFullName();
        String role = user.getRole();

        User isEmailExists = userRepository.findByEmail(email);
        if (isEmailExists != null) {
            throw new Exception("Email is already used with another account!");
        }

        User createUser = new User();
        createUser.setEmail(email);
        createUser.setPassword(password);
        createUser.setFullName(fullName);
        createUser.setRole(role);
        User savedUser = userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register success");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authentication(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authentication(String email, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(email);

        if(userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
