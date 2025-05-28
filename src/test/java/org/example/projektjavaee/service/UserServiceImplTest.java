package org.example.projektjavaee.service;

import org.example.projektjavaee.dao.UserDAO;
import org.example.projektjavaee.model.User;
import org.example.projektjavaee.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserDAO userDAO;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl();
        userService.userDAO = userDAO; // wstrzyknięcie mocka do pola
    }

    @Test
    void testLoginSuccess() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");

        when(userDAO.findByUsername("admin")).thenReturn(user);

        User result = userService.login("admin", "1234");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    void testLoginWrongPassword() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");

        when(userDAO.findByUsername("admin")).thenReturn(user);

        User result = userService.login("admin", "wrong");
        assertNull(result);
    }

    @Test
    void testLoginUserNotFound() {
        when(userDAO.findByUsername("ghost")).thenReturn(null);
        User result = userService.login("ghost", "whatever");
        assertNull(result);
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setUsername("newuser");

        userService.register(user);

        verify(userDAO).create(user);
    }

    @Test
    void testExistsByUsernameWhenExists() {
        when(userDAO.findByUsername("known")).thenReturn(new User());
        assertTrue(userService.existsByUsername("known"));
    }

    @Test
    void testExistsByUsernameWhenNotExists() {
        when(userDAO.findByUsername("unknown")).thenReturn(null);
        assertFalse(userService.existsByUsername("unknown"));
    }
}
