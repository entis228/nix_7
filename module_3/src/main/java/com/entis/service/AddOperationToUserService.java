package com.entis.service;

import com.entis.dao.AccountDao;
import com.entis.dao.OperationDao;
import com.entis.dao.UserDao;
import com.entis.entity.Account;
import com.entis.entity.Operation;
import com.entis.entity.User;
import com.entis.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AddOperationToUserService {

    private final UserDao userDao;
    private final AccountDao accountDao;
    private final OperationDao operationDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddOperationToUserService.class);

    public AddOperationToUserService(UserDao userDao, AccountDao accountDao, OperationDao operationDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.operationDao = operationDao;
    }

    public String addOperation(Long userId, String accountName, Operation operation){
        try {
            LOGGER.info("Trying to add an operation");
            User user = userDao.findById(userId);
            if(user==null)throw new UserNotFoundException();
            List<Account> userAccounts = user.getAccounts();
            Account account = userAccounts.stream().filter(x -> (x.getName().equals(accountName))).findFirst().get();
            operationDao.create(operation);
            List<Operation> accountOperations=account.getOperations();
            accountOperations.add(operation);
            accountDao.update(account);
            LOGGER.info("Operation with id %d was created".formatted(operation.getId()));
            return "Success adding operation";
        }catch (UserNotFoundException e){
            return "User with id "+userId+" was not found";
        }
        catch (Exception e){
            LOGGER.error("Operation was not added ",e);
            throw new RuntimeException(e);
        }
    }
}
