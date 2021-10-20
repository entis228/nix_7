package com.entis.service;

import com.entis.dao.impl.jdbc.JDBCOperationDao;
import com.entis.entity.Operation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveOperationsDuringTimeToCSVService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveOperationsDuringTimeToCSVService.class);
    private final JDBCOperationDao operationDao;

    public SaveOperationsDuringTimeToCSVService(JDBCOperationDao operationDao) {
        this.operationDao = operationDao;
    }

    public void save(Long userId, String accountName, Instant from, Instant to, String filePath) {
        LOGGER.info("Trying to save operations");
        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            List<Operation> accountTimeOperations;
                accountTimeOperations = operationDao.getOperationsByUserIdAndAccountNameInPeriod(userId, accountName, from, to);
            if (accountTimeOperations == null) {
                String errMsg = "Some error during ejecting operations by time";
                LOGGER.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            FileWriter out = new FileWriter(filePath);
            String[] HEADERS = {"category", "description", "time"};
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
                for (Operation op : accountTimeOperations) {
                    printer.printRecord(op.getCategory().getName(), op.getDescription(), op.getTime().toString());
                }
            }
            LOGGER.info("Success save to CSV file at %s".formatted(filePath));
        } catch (IOException e) {
            LOGGER.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }
}
