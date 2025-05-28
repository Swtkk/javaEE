package org.example.projektjavaee.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import org.example.projektjavaee.dao.UserDAO;
import org.example.projektjavaee.model.User;

@Stateless
public class UserServiceImpl implements UserService {
    @Inject
    UserDAO userDAO;
    public User login(String username, String password) {
        try {
            User user = userDAO.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) return user;
        } catch (Exception ignored) {}
        return null;
    }
    public void register(User user) {
        userDAO.create(user);
    }
    public boolean existsByUsername(String username) {
        return userDAO.findByUsername(username) != null;
    }
}
