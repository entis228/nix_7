package com.entis.dao;

import com.entis.entity.Account;
import com.entis.entity.Operation;

import java.time.Instant;
import java.util.List;

public interface OperationDao {

    void create(Operation operation);

    Operation findById(Long id);

    List<Operation>findByTime(Long accountId, Instant from, Instant to);

    void update(Operation operation);

    void deleteById(Long id);
}
