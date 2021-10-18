package com.entis.dao.impl.jdbc;

import com.entis.dao.UserDao;
import com.entis.entity.User;
import com.entis.exception.NonImplementedException;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUserDao implements UserDao, Closeable {

    private final Connection connection;

    public JDBCUserDao(String DBUsername, String DBPassword) {
        Properties props = loadProperties();
        String url = props.getProperty("url");
        try {
            connection = DriverManager.getConnection(url,DBUsername,DBPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties loadProperties() {

        Properties props = new Properties();

        try (InputStream input = JDBCUserDao.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }

    @Override
    public void create(User user) {
        throw new NonImplementedException();
    }

    @Override
    public User findById(Long id) {
        User result=null;
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement getUser = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?")) {

                getUser.setLong(1,id);

                ResultSet resultSet = getUser.executeQuery();

                if (resultSet.next()) {
                    Long dbId=resultSet.getLong("id");
                    String name=resultSet.getString("name");
                    String surName=resultSet.getString("surname");
                    String lastName=resultSet.getString("lastName");
                    String phone=resultSet.getString("phone");
                    result=new User();
                    result.setId(dbId);
                    result.setName(name);
                    result.setSurname(surName);
                    result.setLastName(lastName);
                    result.setPhone(phone);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        if(result==null)throw new RuntimeException("User not found");
        return result;
    }

    @Override
    public void update(User user) {
        throw new NonImplementedException();
    }

    @Override
    public void deleteById(Long id) {
        throw new NonImplementedException();
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
