package com.entis.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.LinkedList;

public class ConsoleMenu {

    private final String[] optArray;
    private final Object objectWithMethods;
    private final LinkedList<String> optListWithoutExit = new LinkedList<>();

    public ConsoleMenu(Object objectWithMethods, String... options) {
        for (String s : options) {
            if (!s.equals("exit")) {
                optListWithoutExit.add(s);
            }
        }
        optArray = optListWithoutExit.toArray(new String[0]);
        this.objectWithMethods = objectWithMethods;
    }

    private static void clearScreen() {
        for (int i = 0; i < 50; i++)
            System.out.println();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renameOption(int index,String newName){
        optListWithoutExit.set(index,newName);
    }

    public void loop() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("================");
            System.out.println("======Menu======");
            for (int i = 1; i <= optArray.length; i++) {
                System.out.println(i + "-" + optListWithoutExit.get(i));
            }
            System.out.println("0-exit");
            System.out.println("================");
            System.out.println("Choose your option number");
            String input = reader.readLine();
            int selectedNumber;
            try {
                selectedNumber = Integer.parseInt(input);
                if (selectedNumber < 0 || selectedNumber > optArray.length) {
                    throw new NumberFormatException();
                }
                if (selectedNumber == 0)
                    break;
                Method method = objectWithMethods.getClass().getDeclaredMethod(optArray[selectedNumber - 1]);
                method.setAccessible(true);
                method.invoke(objectWithMethods);
            } catch (Exception e) {
                if (e instanceof NumberFormatException) {
                    System.out.println("Incorrect input");
                } else
                    System.out.println(e.getMessage());
            }
            System.out.println("Press any button");
            reader.readLine();
            clearScreen();
        }
    }
}
