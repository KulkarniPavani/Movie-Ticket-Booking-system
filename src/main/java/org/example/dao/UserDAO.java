package org.example.dao;

import org.example.model.User;

public interface UserDAO {

    boolean addUser(User user);

    User readUser(int userId);

    boolean removeUser(int userId);

    User findUserByEmail(String email);

    User loginUser(String email, String password, String role);
}