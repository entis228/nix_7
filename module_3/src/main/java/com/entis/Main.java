package com.entis;

import com.entis.controller.BusinessController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        BusinessController controller = new BusinessController(args);
        System.out.println("Select operation (1-add operation, 2-save operations to csv)");
        try (BufferedReader reader=new BufferedReader(new InputStreamReader(System.in))){
            String input = reader.readLine();
            if(input.equals("1"))
                controller.addOperation();
            else if(input.equals("2")){
                controller.saveOperationsDuringPeriodToCSV();
            }else System.out.println("Incorrect choice");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
