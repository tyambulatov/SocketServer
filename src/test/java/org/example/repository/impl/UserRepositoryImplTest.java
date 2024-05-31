package org.example.repository.impl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class UserRepositoryImplTest {
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void save() throws SQLException {
        User user = new User(null, "login7", "password7");
        userRepository.save(user);

        String expected = new User(7L, "login7", "password7").toString();
        String actual = userRepository.findById(9L).get().toString();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void findById() {
        String expected = new User(1L, "login1", "password1").toString();
        String actual = userRepository.findById(1L).get().toString();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() throws SQLException {
        System.out.println(userRepository.findAll());
    }

    @Test
    void update() {
        String expected = new User(2L, "loginUpdated", "passwordUpdated").toString();

        User updatedUser = new User(2L, "loginUpdated", "passwordUpdated");
        String actual = userRepository.update(updatedUser).toString();

        Assertions.assertEquals(expected, actual);

        userRepository.update(new User(2L, "login2", "password2"));
    }

    @Test
    void deleteById() {
        userRepository.deleteById(7L);
    }
}