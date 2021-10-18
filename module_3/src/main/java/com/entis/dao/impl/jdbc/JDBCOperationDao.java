package com.entis.dao.impl.jdbc;

import com.entis.dao.OperationDao;
import com.entis.entity.Operation;
import com.entis.exception.NonImplementedException;

import java.time.Instant;
import java.util.List;

public class JDBCOperationDao implements OperationDao {

    private final String DBUsername;
    private final String DBPassword;

    public JDBCOperationDao(String DBUsername, String DBPassword) {
        this.DBPassword=DBPassword;
        this.DBUsername=DBUsername;
    }

    @Override
    public void create(Operation operation) {
        throw new NonImplementedException();
    }

    @Override
    public Operation findById(Long id) {
        throw new NonImplementedException();
    }

    @Override
    public List<Operation> findByTime(Long accountId, Instant from, Instant to) {
        throw new NonImplementedException();
    }

    @Override
    public void update(Operation operation) {
        throw new NonImplementedException();
    }

    @Override
    public void deleteById(Long id) {
        throw new NonImplementedException();
    }
}
