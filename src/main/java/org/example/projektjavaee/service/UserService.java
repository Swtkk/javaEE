package org.example.projektjavaee.service;

import org.example.projektjavaee.model.User;

public interface UserService {
    User login(String username, String password);
}
