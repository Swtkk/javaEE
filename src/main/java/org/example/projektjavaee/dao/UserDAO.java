package org.example.projektjavaee.dao;

import org.example.projektjavaee.model.User;

public interface UserDAO {
    User findByUsername(String username);
    void create(User user);
}
