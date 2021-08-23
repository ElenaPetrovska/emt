package com.ukim.finki.onlineshopseminarska.service;

import com.ukim.finki.onlineshopseminarska.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User signUpUser(String username, String password, String repeatedPassword);
}
