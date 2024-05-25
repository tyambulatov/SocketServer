package org.example.service.impl;

import org.example.dto.UserForm;
import org.example.exception.UserNotFound;
import org.example.model.User;
import org.example.repository.Repository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UsersService;

import java.util.List;
import java.util.Optional;

public class UsersServiceImpl implements UsersService {
    private final Repository<User, Long, UserForm> userRepository = new UserRepositoryImpl();

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(UserForm userForm) {
        userRepository.save(userForm);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        User answer = null;
        if (foundUser.isPresent()) {
            answer = foundUser.get();
        }
        return answer;
    }

    @Override
    public void updateUser(Long userId, UserForm userForm) {
        Optional<User> userToUpdate = userRepository.findById(userId);
        if (userToUpdate.isPresent()) {
            User updatedUser = new User(userId, userForm.login(), userForm.password());
            userRepository.update(updatedUser);
        } else {
            throw new UserNotFound();
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
