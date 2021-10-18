package com.entis.dao.impl.jpa;

import com.entis.dao.AccountDao;
import com.entis.entity.Account;
import com.entis.exception.NonImplementedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.io.Closeable;

public class JPAccountDao implements AccountDao, Closeable {
    private static final Configuration configuration = new Configuration().configure();
    private final String dbUsername;
    private final String dbPassword;
    private SessionFactory currentFactory;
    private final EntityManager manager;

    public JPAccountDao(String dbUsername, String dbPassword) {
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
    public void create(Account account) {
        throw new NonImplementedException();
    }

    @Override
    public Account findById(Long id) {
        return manager.find(Account.class,id);
    }

    @Override
    public void update(Account account) {
        try {
            manager.getTransaction().begin();
            Account loaded = findById(account.getId());
            loaded.setBalance(account.getBalance());
            loaded.setOperations(account.getOperations());
            loaded.setName(account.getName());
            manager.getTransaction().commit();
        }catch (Exception e){
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
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
