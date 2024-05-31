package org.example.service;

import org.example.exception.NotFoundException;
import org.example.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void addUser(User user);

    User getUser(Long id) throws NotFoundException;

    void updateUser(Long userId, User userForm);

    void deleteUser(Long userId);
}
