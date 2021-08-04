package com.entis;

import com.entis.game.console.GameOfLife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String clearScreenCommand = "\033[H\033[2J";

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) System.out.println();
        System.out.println(clearScreenCommand);
    }

    public static void main(String[] args) {
        while (true) {
            clearScreen();
            System.out.println("===============");
            System.out.println("===The Life====");
            System.out.println("1-Start game");
            System.out.println("2-exit game");
            System.out.println("===============");
            try {
                String input = reader.readLine();
                if (input.equals("1")) {
                    GameOfLife.main(new String[1]);
                } else {
                    if (input.equals("2"))
                        break;
                    else System.out.println("Incorrect input");
                    System.out.println("Press any button");
                    reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                if (e.getMessage().equals("error size")) {
                    System.out.println("Size must be > 2");
                    System.out.println("Press any button");
                    try {
                        reader.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
