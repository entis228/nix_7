package com.entis.dao;

import com.entis.entity.Account;

public interface AccountDao {

    void create(Account account);

    Account findById(Long id);

    void update(Account account);

    void deleteById(Long id);
}
