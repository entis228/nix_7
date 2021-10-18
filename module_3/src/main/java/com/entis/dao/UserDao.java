package com.entis.dao;

import com.entis.entity.User;

public interface UserDao {

    void create(User user);

    User findById(Long id);

    void update(User user);

    void deleteById(Long id);
}
