package com.entis.controller;

import com.entis.entity.category.Category;
import com.entis.entity.category.impl.Expense;
import com.entis.entity.category.impl.Income;
import com.entis.exception.IncorrectDateException;
import com.entis.facade.OperationFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.regex.Pattern;

public class BusinessController {

    private final String[] args;
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final OperationFacade facade = new OperationFacade();

    public BusinessController(String[] args) {
        this.args = args;
    }

    public void addOperation() {
        try {
            System.out.println("Hello user!");
            System.out.println("Enter the account name: ");
            String accountName=reader.readLine();
            System.out.println("Choose the category: 1-income 2-expends");
            int inputCategory=Integer.parseInt(reader.readLine());
            Category category = switch (inputCategory) {
                case 1 -> new Income();
                case 2 -> new Expense();
                default -> throw new NumberFormatException();
            };
            System.out.println("Input description of operation");
            String description = reader.readLine();
            System.out.println("Input date like such example: 2011-12-03T10:15:30");
            String time=reader.readLine();
            String regex="\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
            Pattern pattern=Pattern.compile(regex);
            boolean isMatch = pattern.matcher(time).matches();
            if(!isMatch)throw new IncorrectDateException();
            String resultOut=facade.add(args[0],args[1], Long.parseLong(args[2]),accountName,category,description,time);
            System.out.println(resultOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (NumberFormatException e){
            System.out.println("Incorrect number of choice (must be 1 or 2)");
        }catch (IncorrectDateException e){
            System.out.println("Incorrect date format");
        }
    }

    public void saveOperationsDuringPeriodToCSV() {
        try {
//            facade.save(args[0],args[1], );
            reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
