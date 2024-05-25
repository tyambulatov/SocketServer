package org.example.repository.mapper;

import org.example.model.User;

import java.sql.ResultSet;
import java.util.Optional;

@FunctionalInterface
public interface UserResultSetMapper {
    public User parse(ResultSet row);
}
