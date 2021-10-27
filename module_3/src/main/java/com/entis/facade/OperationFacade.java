package com.entis.facade;

import com.entis.dao.impl.jdbc.JDBCOperationDao;
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
        try(AddOperationToUserService addOperation = new AddOperationToUserService(dbUser,dbPassword)){
            return addOperation.addOperation(userId, accountName, operation);
        }
    }

    public void save(String dbUser, String dbPassword, Long userId,
                     String accountName, String fromInst, String toInst, String filePath) {
        try(JDBCOperationDao jdbcOperationDao=new JDBCOperationDao(dbUser,dbPassword);) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            Instant from = LocalDateTime.parse(fromInst, formatter).toInstant(ZoneOffset.UTC);
            Instant to = LocalDateTime.parse(toInst, formatter).toInstant(ZoneOffset.UTC);
            SaveOperationsDuringTimeToCSVService service = new SaveOperationsDuringTimeToCSVService(jdbcOperationDao);
            service.save(userId, accountName, from, to, filePath);
        }
    }
}
