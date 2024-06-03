package ru.yambulatov.learn.repository.mapper;

import ru.yambulatov.learn.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetMapper {

    public User parse(ResultSet row) {
        try {
            return new User(
                    row.getLong(1),
                    row.getString(2),
                    row.getString(3)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error parsing user from ResultSet", e);
        }
    }
}
