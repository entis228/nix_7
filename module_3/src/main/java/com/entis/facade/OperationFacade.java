package com.entis.facade;

import com.entis.dao.AccountDao;
import com.entis.dao.OperationDao;
import com.entis.dao.UserDao;
import com.entis.dao.impl.jpa.JPAOperationDao;
import com.entis.dao.impl.jpa.JPAUserDao;
import com.entis.dao.impl.jpa.JPAccountDao;
import com.entis.entity.Operation;
import com.entis.entity.category.Category;
import com.entis.service.AddOperationToUserService;
import com.entis.service.SaveOperationsDuringTimeToCSVService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class OperationFacade {

    private SaveOperationsDuringTimeToCSVService saveOperation;
    private static final DateTimeFormatter formatter=DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public String add(String dbUser, String dbPassword, Long userId,
                    String accountName, Category category, String description, String time){
        Operation operation=new Operation();
        operation.setCategory(category);
        operation.setDescription(description);
        Instant ldt = LocalDateTime.parse( time, formatter).toInstant(ZoneOffset.UTC);
        operation.setTime(ldt);
        try (JPAUserDao userDao=new JPAUserDao(dbUser, dbPassword);
        JPAccountDao accountDao=new JPAccountDao(dbUser, dbPassword);
        JPAOperationDao operationDao=new JPAOperationDao(dbUser, dbPassword);) {
            AddOperationToUserService addOperation = new AddOperationToUserService(userDao, accountDao, operationDao);
            return addOperation.addOperation(userId, accountName, operation);
        }
    }

    public void save(String dbUser, String dbPassword,Long userId,
                     String accountName,String fromInst, String toInst, String filePath){

    }
}
