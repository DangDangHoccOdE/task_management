package com.example.service;

import com.example.model.User;

import java.util.List;

public interface UserService {
    User getUserProfiles(String jwt);

    List<User> getAllUsers();
}
