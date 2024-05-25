package org.example.repository.impl;

import org.example.dto.UserForm;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

class UserRepositoryImplTest {
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void save() throws SQLException {
        UserForm userForm = new UserForm("login7", "password7");
        userRepository.save(userForm);

        String expected = new User(7L, "login7", "password7").toString();
        String actual = userRepository.findById(9L).get().toString();
        assertEquals(expected, actual);

    }

    @Test
    void findById() {
        String expected = new User(1L, "login1", "password1").toString();
        String actual = userRepository.findById(1L).get().toString();
        assertEquals(expected, actual);
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

        assertEquals(expected, actual);

        userRepository.update(new User(2L, "login2", "password2"));
    }

    @Test
    void deleteById() {
        userRepository.deleteById(7L);
    }
}