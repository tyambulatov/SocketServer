package ru.yambulatov.learn.db.impl;

import ru.yambulatov.learn.Main;
import ru.yambulatov.learn.db.ConnectionManager;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {

    private static final DataSource dataSource = configHikariDataSource(configProperties());

    private static Properties configProperties() {
        Properties dbProperties = new Properties();

        try {
            dbProperties.load(new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(Main.class.getResourceAsStream("/db.properties")))));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return dbProperties;
    }

    private static javax.sql.DataSource configHikariDataSource(Properties dbProperties) {
        com.zaxxer.hikari.HikariDataSource dataSource = new com.zaxxer.hikari.HikariDataSource();
        dataSource.setPassword(dbProperties.getProperty("db.password"));
        dataSource.setUsername(dbProperties.getProperty("db.username"));
        dataSource.setJdbcUrl(dbProperties.getProperty("db.url"));
        dataSource.setMaximumPoolSize(
                Integer.parseInt(
                        dbProperties.getProperty("db.hikari.MaxPoolSize")));
        return dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}