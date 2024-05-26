package org.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.example.dto.UserForm;
import org.example.exception.NotFoundException;
import org.example.model.User;
import org.example.repository.Repository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UsersService;

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
    public User getUser(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void updateUser(Long userId, UserForm userForm) {
        Optional<User> userToUpdate = userRepository.findById(userId);
        if (userToUpdate.isPresent()) {
            User updatedUser = new User(userId, userForm.login(), userForm.password());
            userRepository.update(updatedUser);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
