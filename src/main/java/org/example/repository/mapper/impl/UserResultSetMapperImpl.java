package org.example.repository.mapper.impl;

import org.example.model.User;
import org.example.repository.mapper.UserResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapperImpl implements UserResultSetMapper {

    @Override
    public User parse(ResultSet row) {
        try {
            return new User(
                    row.getLong(1),
                    row.getString(2),
                    row.getString(3)
            );
        } catch (SQLException e) {
            System.err.println("Error parsing user from ResultSet");;
            return null;
        }
    }
}
