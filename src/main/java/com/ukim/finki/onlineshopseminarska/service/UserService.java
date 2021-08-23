package com.ukim.finki.onlineshopseminarska.service;

import com.ukim.finki.onlineshopseminarska.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(String userId);
    User registerUser(User user);
}
