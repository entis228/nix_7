package com.entis.controller;

import com.entis.service.DistinctNameService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NamesController {

    private static final DistinctNameService service =new DistinctNameService();
    private static final BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));

    public void addName(){
        try {
            System.out.println("Enter the name");
            String input=reader.readLine();
            if(!input.contains(" ")){
                service.addName(input);
            }else System.out.println("The name mustn't contains spaces");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addManyNames(){
        try {
            System.out.println("Enter the name and press enter, if you want to stop, enter stop");
            while (true) {
                String input = reader.readLine();
                if(input.equals("stop"))break;
                if(!input.contains(" ")){
                    service.addName(input);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void findFirstDistinctName(){
        System.out.println(service.getDistinct());
    }
}
