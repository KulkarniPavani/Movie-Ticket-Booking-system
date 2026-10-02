package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

public class UserController {

    private final UserService userService =
            new UserServiceImpl();

    public boolean addUser(User user) {
        return userService.addUser(user);
    }

    public User readUser(int userId) {
        return userService.readUser(userId);
    }

    public boolean removeUser(int userId) {
        return userService.removeUser(userId);
    }

    public User findUserByEmail(String email) {
        return userService.findUserByEmail(email);
    }

    public User loginUser(String email, String password, String role) {
        return userService.loginUser(email, password, role);
    }
}