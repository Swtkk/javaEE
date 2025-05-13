package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.example.projektjavaee.dao.UserDAO;
import org.example.projektjavaee.model.User;

@Stateless
public class UserServiceImpl implements UserService {
    @Inject
    private UserDAO userDAO;
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) return user;
        return null;
    }
}