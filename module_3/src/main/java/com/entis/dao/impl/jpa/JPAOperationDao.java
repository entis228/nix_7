package com.entis.dao.impl.jpa;

import com.entis.dao.OperationDao;
import com.entis.entity.Operation;
import com.entis.exception.NonImplementedException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import java.io.Closeable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JPAOperationDao implements OperationDao, Closeable {
    private static final Configuration configuration = new Configuration().configure();
    private final String dbUsername;
    private final String dbPassword;
    private SessionFactory currentFactory;
    private final EntityManager manager;

    public JPAOperationDao(String dbUsername, String dbPassword) {
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
    public void create(Operation operation) {
        try {
            operation.setId(generateId());
            manager.getTransaction().begin();
            manager.persist(operation);
            manager.getTransaction().commit();
        }catch (Exception e){
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
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

    @Override
    public void close() {
        if(currentFactory!=null&&!currentFactory.isClosed())
            currentFactory.close();
    }

    private Long generateId() {
        String rawId = UUID.randomUUID().toString();
        StringBuilder builder=new StringBuilder();
        rawId.chars().filter(Character::isDigit).forEach(builder::append);
        return Long.getLong(builder.toString());
    }
}
