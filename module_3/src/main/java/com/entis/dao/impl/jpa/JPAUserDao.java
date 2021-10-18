package com.entis.dao.impl.jpa;

import com.entis.dao.UserDao;
import com.entis.entity.Account;
import com.entis.entity.User;
import com.entis.exception.NonImplementedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.io.Closeable;
import java.io.IOException;

public class JPAUserDao implements UserDao, Closeable {
    private static final Configuration configuration = new Configuration().configure();
    private final String dbUsername;
    private final String dbPassword;
    private SessionFactory currentFactory;
    private final EntityManager manager;

    public JPAUserDao(String dbUsername, String dbPassword) {
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        openFactory();
        manager= currentFactory.createEntityManager();
    }

    private void openFactory(){
        configuration.setProperty("hibernate.connection.username",dbUsername);
        configuration.setProperty("hibernate.connection.password",dbPassword);
        currentFactory = configuration.buildSessionFactory();
    }

    @Override
    public void create(User user) {
        throw new NonImplementedException();
    }

    @Override
    public User findById(Long id) {
        return manager.find(User.class,id);
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
    public void close() {
        if(currentFactory!=null&&!currentFactory.isClosed())
            currentFactory.close();
    }
}
