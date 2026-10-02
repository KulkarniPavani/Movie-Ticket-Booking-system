package org.example.service;

import org.example.dao.UserDAO;
import org.example.dao.UserDAOImpl;
import org.example.model.User;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean addUser(User user) {

        if (user == null ||
                user.getName() == null ||
                user.getName().isBlank() ||
                user.getEmail() == null ||
                user.getEmail().isBlank() ||
                user.getPassword() == null ||
                user.getPassword().isBlank()) {

            return false;
        }

        return userDAO.addUser(user);
    }

    @Override
    public User readUser(int userId) {

        if (userId <= 0) {
            return null;
        }

        return userDAO.readUser(userId);
    }

    @Override
    public boolean removeUser(int userId) {

        if (userId <= 0) {
            return false;
        }

        return userDAO.removeUser(userId);
    }

    @Override
    public User findUserByEmail(String email) {

        if (email == null || email.isBlank()) {
            return null;
        }

        return userDAO.findUserByEmail(email);
    }

    @Override
    public User loginUser(String email, String password, String role) {

        if (email == null || email.isBlank() ||
                password == null || password.isBlank() ||
                role == null || role.isBlank()) {

            return null;
        }

        return userDAO.loginUser(email, password, role);
    }
}