package org.example.dao;

import org.example.model.User;
import org.example.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(UserDAOImpl.class);

    private static final String ADD_USER =
            "INSERT INTO users " +
                    "(name, email, phone, password, role) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String READ_USER =
            "SELECT * FROM users WHERE user_id = ?";

    private static final String REMOVE_USER =
            "DELETE FROM users WHERE user_id = ?";

    private static final String FIND_USER_BY_EMAIL =
            "SELECT * FROM users WHERE email = ?";

    private static final String LOGIN_USER =
            "SELECT * FROM users " +
                    "WHERE email = ? AND password = ? AND role = ?";


    @Override
    public boolean addUser(User user) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_USER)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("User added successfully");
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while adding user", e);
        }

        return false;
    }


    @Override
    public User readUser(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(READ_USER)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));

                logger.info("User found: userId={}", userId);

                return user;
            }

        } catch (SQLException e) {
            logger.error("Error while finding user", e);
        }

        return null;
    }


    @Override
    public boolean removeUser(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(REMOVE_USER)) {

            statement.setInt(1, userId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info(
                        "User deleted successfully: userId={}",
                        userId
                );
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error while deleting user", e);
        }

        return false;
    }


    @Override
    public User findUserByEmail(String email) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_USER_BY_EMAIL)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));

                logger.info("User found by email");

                return user;
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while finding user by email",
                    e
            );
        }

        return null;
    }


    @Override
    public User loginUser(String email, String password, String role) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(LOGIN_USER)) {

            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, role);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));

                logger.info(
                        "Login successful: email={}, role={}",
                        email,
                        role
                );

                return user;
            }

            logger.warn(
                    "Login failed: email={}, role={}",
                    email,
                    role
            );

        } catch (SQLException e) {
            logger.error("Error while logging in user", e);
        }

        return null;
    }
}