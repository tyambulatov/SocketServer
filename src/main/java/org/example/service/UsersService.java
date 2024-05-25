package org.example.service;

import org.example.model.User;
import org.example.dto.UserForm;

import java.util.List;

public interface UsersService {

    List<User> getAllUsers();

    void addUser(UserForm userForm);

    User getUser(Long id);

    void updateUser(Long userId, UserForm userForm);

    void deleteUser(Long userId);
}
