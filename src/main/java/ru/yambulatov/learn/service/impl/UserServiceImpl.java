package ru.yambulatov.learn.service.impl;

import java.util.List;

import ru.yambulatov.learn.exception.NotFoundException;
import ru.yambulatov.learn.model.User;
import ru.yambulatov.learn.repository.Repository;
import ru.yambulatov.learn.service.UserService;

public class UserServiceImpl implements UserService {
    private final Repository<User, Long> userRepository;

    public UserServiceImpl(Repository<User, Long> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void updateUser(Long userId, User user) {
        if (isUserInRepository(userId)) {
            User updatedUser = new User(userId, user.login(), user.password());
            userRepository.update(updatedUser);
        } else {
            throw new NotFoundException();
        }
    }

    private boolean isUserInRepository(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
