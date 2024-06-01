package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.impl.ConnectionManagerImpl;
import org.example.exception.InternalServerErrorException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.mapper.UserResultSetMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    //language=SQL
    private static final String SQL_INSERT = "insert into account(login, password) values (?, ?) returning id, login, password";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from account where id=? ";

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from account order by id";

    //language=SQL
    private static final String SQL_UPDATE = "update account set login=?, password=? where id=? returning id, login, password";

    //language=SQL
    private static final String SQL_DELETE = "delete from account where id=? ";

    private static final UserResultSetMapper userResultSetMapper = new UserResultSetMapper();

    private static final ConnectionManager connectionManager = new ConnectionManagerImpl();

    @Override
    public User save(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

            statement.setString(1, user.login());
            statement.setString(2, user.password());

            try (ResultSet resultSet = statement.executeQuery()) {
                User savedUser = null;
                if (resultSet.next()) {
                    savedUser = userResultSetMapper.parse(resultSet);
                }
                return savedUser;
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                Optional<User> result = Optional.empty();
                if (resultSet.next()) {
                    result = Optional.ofNullable(userResultSetMapper.parse(resultSet));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public List<User> findAll() {

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {

                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = userResultSetMapper.parse(resultSet);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public User update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, user.login());
            statement.setString(2, user.password());
            statement.setLong(3, user.id());

            try (ResultSet resultSet = statement.executeQuery()) {
                User updatedUser = null;
                if (resultSet.next()) {
                    updatedUser = userResultSetMapper.parse(resultSet);
                }
                return updatedUser;
            }

        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

            statement.setLong(1, id);

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
