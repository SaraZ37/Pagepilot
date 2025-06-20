package com.Pagepilot.Pagepilot.user;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public User findById(String userId) {
        return userRepository.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
