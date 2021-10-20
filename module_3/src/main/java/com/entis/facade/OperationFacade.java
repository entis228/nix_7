package com.entis.facade;

import com.entis.dao.impl.jdbc.JDBCOperationDao;
import com.entis.dao.impl.jpa.JPAOperationDao;
import com.entis.dao.impl.jpa.JPAUserDao;
import com.entis.dao.impl.jpa.JPAccountDao;
import com.entis.entity.Operation;
import com.entis.entity.category.Category;
import com.entis.entity.category.impl.Expense;
import com.entis.entity.category.impl.Income;
import com.entis.service.AddOperationToUserService;
import com.entis.service.SaveOperationsDuringTimeToCSVService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OperationFacade {

    private SaveOperationsDuringTimeToCSVService saveOperation;
    private JPAUserDao userDao;
    private JPAccountDao accountDao;
    private JPAOperationDao operationDao;
    private JDBCOperationDao jdbcOperationDao;

    public String add(String dbUser, String dbPassword, Long userId,
                      String accountName, String category, String description) {
        Operation operation = new Operation();
        Category savedCategory;
        if (category.equals("1")) savedCategory = new Income();
        else savedCategory = new Expense();
        savedCategory.setId(1L);
        operation.setCategory(savedCategory);
        operation.setDescription(description);
        operation.setTime(Instant.now());
        userDao = new JPAUserDao(dbUser, dbPassword);
        accountDao = new JPAccountDao(dbUser, dbPassword);
        operationDao = new JPAOperationDao(dbUser, dbPassword);
        AddOperationToUserService addOperation = new AddOperationToUserService(userDao, accountDao, operationDao);
        return addOperation.addOperation(userId, accountName, operation);
    }

    public void save(String dbUser, String dbPassword, Long userId,
                     String accountName, String fromInst, String toInst, String filePath) {
        if(jdbcOperationDao==null){
            jdbcOperationDao=new JDBCOperationDao(dbUser,dbPassword);
        }
        DateTimeFormatter formatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        Instant from= LocalDateTime.parse(fromInst,formatter).toInstant(ZoneOffset.UTC);
        Instant to= LocalDateTime.parse(toInst,formatter).toInstant(ZoneOffset.UTC);
        SaveOperationsDuringTimeToCSVService service=new SaveOperationsDuringTimeToCSVService(jdbcOperationDao);
        service.save(userId,accountName,from,to,filePath);
    }

    public void close(){
        if(userDao!=null)userDao.close();
        if(accountDao!=null)accountDao.close();
        if(operationDao!=null)operationDao.close();
        if(jdbcOperationDao!=null)jdbcOperationDao.close();
    }
}
