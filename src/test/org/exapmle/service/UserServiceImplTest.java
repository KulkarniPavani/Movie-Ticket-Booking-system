package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserDAO userDAO;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userDAO = Mockito.mock(UserDAO.class);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void testAddUser() {

        User user = new User();
        user.setName("Pavani");
        user.setEmail("pavani@gmail.com");
        user.setPassword("password");
        user.setPhone("9876543210");
        user.setRole("USER");

        when(userDAO.addUser(user)).thenReturn(true);

        boolean result = userService.addUser(user);

        assertTrue(result);

        verify(userDAO).addUser(user);
    }

    @Test
    void testReadUser() {

        User user = new User();
        user.setUserId(1);
        user.setName("Pavani");

        when(userDAO.readUser(1)).thenReturn(user);

        User result = userService.readUser(1);

        assertNotNull(result);
        assertEquals("Pavani", result.getName());

        verify(userDAO).readUser(1);
    }

    @Test
    void testRemoveUser() {

        when(userDAO.removeUser(1)).thenReturn(true);

        boolean result = userService.removeUser(1);

        assertTrue(result);

        verify(userDAO).removeUser(1);
    }

    @Test
    void testFindUserByEmail() {

        User user = new User();
        user.setUserId(1);
        user.setEmail("pavani@gmail.com");

        when(userDAO.findUserByEmail("pavani@gmail.com"))
                .thenReturn(user);

        User result = userService.findUserByEmail("pavani@gmail.com");

        assertNotNull(result);
        assertEquals("pavani@gmail.com", result.getEmail());

        verify(userDAO).findUserByEmail("pavani@gmail.com");
    }
}