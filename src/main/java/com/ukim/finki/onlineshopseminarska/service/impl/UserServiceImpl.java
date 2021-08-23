package com.ukim.finki.onlineshopseminarska.service.impl;

import com.ukim.finki.onlineshopseminarska.model.User;
import com.ukim.finki.onlineshopseminarska.model.exception.UserAlreadyExistsException;
import com.ukim.finki.onlineshopseminarska.model.exception.UserNotFoundException;
import com.ukim.finki.onlineshopseminarska.repository.UserRepository;
import com.ukim.finki.onlineshopseminarska.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException(userId));
    }

    @Override
    public User registerUser(User user) {
        if(this.userRepository.existsById(user.getUsername())){
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
