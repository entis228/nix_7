package com.entis.dao.impl.jdbc;

import com.entis.entity.Operation;
import com.entis.entity.category.impl.Expense;
import com.entis.entity.category.impl.Income;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCOperationDao implements Closeable {

    private final Connection connection;

    public JDBCOperationDao(String DBUsername, String DBPassword) {
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

        try (InputStream input = JDBCOperationDao.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }

    public List<Operation> getOperationsByUserIdAndAccountNameInPeriod(Long user_id, String accountName, Instant dateFrom, Instant dateTo) {
        List<Operation>operations=new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            try(PreparedStatement getAccount = connection.prepareStatement(
                    "SELECT description, time, category_id FROM operations INNER JOIN accounts ON accounts.id = operations.account_id WHERE accounts.user_id = ? and public.accounts.name= ? and operations.time BETWEEN ? and ?")) {

                getAccount.setLong(1,user_id);
                getAccount.setString(2,accountName);
                getAccount.setTimestamp(3,Timestamp.from(dateFrom));
                getAccount.setTimestamp(4,Timestamp.from(dateTo));
                ResultSet resultSet = getAccount.executeQuery();

                while (resultSet.next()) {
                    Operation operation=new Operation();
                    operation.setTime(resultSet.getTimestamp("time").toInstant());
                    operation.setDescription(resultSet.getString("description"));
                    String dbCategoryValue=resultSet.getString("category_id");
                    if(dbCategoryValue==null||dbCategoryValue.equals("2")){
                        operation.setCategory(new Expense());
                    }else operation.setCategory(new Income());
                    operations.add(operation);
                }
                return operations;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
