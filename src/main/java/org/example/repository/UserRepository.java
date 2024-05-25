package org.example.repository;

import org.example.dto.UserForm;
import org.example.model.User;

public interface UserRepository extends Repository<User, Long, UserForm> {}
