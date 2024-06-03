package ru.yambulatov.learn.service;

import ru.yambulatov.learn.exception.NotFoundException;
import ru.yambulatov.learn.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void addUser(User user);

    User getUser(Long id) throws NotFoundException;

    void updateUser(Long userId, User userForm);

    void deleteUser(Long userId);
}
