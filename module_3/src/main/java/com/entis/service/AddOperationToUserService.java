package com.entis.service;

import com.entis.entity.Account;
import com.entis.entity.Operation;
import com.entis.entity.User;
import com.entis.entity.category.Category;
import com.entis.exception.AccountNotFoundException;
import com.entis.exception.UserNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.io.Closeable;

public class AddOperationToUserService implements Closeable {

    private static final Configuration configuration = new Configuration().configure();
    private final String dbUsername;
    private final String dbPassword;
    private SessionFactory currentFactory;
    private final EntityManager manager;

    public AddOperationToUserService(String dbUsername, String dbPassword) {
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AddOperationToUserService.class);

    public String addOperation(Long userId, String accountName, Operation operation){
        try {
            LOGGER.info("Trying to add an operation");
            manager.getTransaction().begin();
            User dbUser=manager.find(User.class,userId);
            Account neededAcc=dbUser.getAccounts().stream().filter(x->x.getName().equals(accountName)).findFirst().get();
            manager.merge(neededAcc);
            Category dbCategory=manager.find(operation.getCategory().getClass(),1L);
            operation.setCategory(dbCategory);
            manager.persist(operation);
            neededAcc.getOperations().add(operation);
            operation.setAccount(neededAcc);
            manager.getTransaction().commit();
            LOGGER.info("Operation with id %d was created".formatted(operation.getId()));
            return "Success adding operation";
        }catch (UserNotFoundException e){
            LOGGER.error("Operation was not added ",e);
            return "User with id "+userId+" was not found";
        }catch (AccountNotFoundException e){
            LOGGER.error("Operation was not added ",e);
            return "Account with name "+accountName+" not found";
        }
        catch (Exception e){
            LOGGER.error("Operation was not added ",e);
            throw new RuntimeException(e);
        }
    }

    public void close(){
        if(currentFactory!=null&&!currentFactory.isClosed())
            currentFactory.close();
    }
}
