package com.entis.controller;

import com.entis.exception.IncorrectDateException;
import com.entis.facade.OperationFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            String category = switch (inputCategory) {
                case 1 -> "1";
                case 2 -> "2";
                default -> throw new NumberFormatException();
            };
            System.out.println("Input description of operation");
            String description = reader.readLine();
            String resultOut=facade.add(args[0],args[1], Long.parseLong(args[2]),accountName,category,description);
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
            System.out.println("Input account name");
            String accountName=reader.readLine();
            System.out.println("Input first date (e.g 2011-05-12T16:30:00)");
            String firstDate=reader.readLine();
            if(isDateNotCorrect(firstDate))throw new IncorrectDateException();
            System.out.println("Input second date (e.g 2021-11-12T16:30:00)");
            String secondDate=reader.readLine();
            if(isDateNotCorrect(secondDate))throw new IncorrectDateException();
            facade.save(args[0],args[1],Long.parseLong(args[2]),accountName,firstDate,secondDate,args[3]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (IncorrectDateException e){
            System.out.println("This date is not correct, try again");
        }
    }

    private boolean isDateNotCorrect(String date){
        String regex="\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2})?";
        Pattern pattern=Pattern.compile(regex);
        return !pattern.matcher(date).matches();
    }
}
