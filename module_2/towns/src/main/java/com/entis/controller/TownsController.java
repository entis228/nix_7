package com.entis.controller;

import com.entis.service.TownsPrice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TownsController {

    private static final String fileInput = "input.txt";
    private static final TownsPrice TOWNS_PRICE = new TownsPrice();

    public void minimalDistance() {
        try {
            Path path = Paths.get(fileInput);
            System.out.println("File content:");
            ArrayList<String> graphStringFromFile = (ArrayList<String>) Files.readAllLines(path);
            graphStringFromFile.forEach(System.out::println);
            System.out.println("-------------------------------");
            System.out.println("output.txt created, content:");
            TOWNS_PRICE.findWaysInGraph(graphStringFromFile);
        }
        catch (NoSuchFileException e){
            System.out.println("input.txt not found, please create it in towns directory");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
