package com.entis.service;

import com.entis.dao.OperationDao;
import com.entis.dao.UserDao;
import com.entis.entity.Account;
import com.entis.entity.Operation;
import com.entis.entity.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveOperationsDuringTimeToCSVService {

    private final UserDao userDao;
    private final OperationDao operationDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveOperationsDuringTimeToCSVService.class);

    public SaveOperationsDuringTimeToCSVService(UserDao userDao, OperationDao operationDao) {
        this.userDao = userDao;
        this.operationDao = operationDao;
    }

    public void save(Long userId, String accountName, Instant from, Instant to, String filePath) {
        LOGGER.info("Trying to save operations");
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));
            User user = userDao.findById(userId);
            List<Account> userAccounts = user.getAccounts();
            Account account = userAccounts.stream().filter(x -> (x.getName().equals(accountName))).findFirst().get();
            List<Operation> accountTimeOperations = operationDao.findByTime(account.getId(), from, to);
            FileWriter out = new FileWriter(filePath);
            String[] HEADERS = {"category", "description", "time"};
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
                for (Operation op : accountTimeOperations) {
                    printer.printRecord(op.getCategory().getName(), op.getDescription(), formatter.format(op.getTime()));
                }
            }
            LOGGER.info("Success save to CSV file at %s".formatted(filePath));
        } catch (IOException e) {
            LOGGER.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }
}
