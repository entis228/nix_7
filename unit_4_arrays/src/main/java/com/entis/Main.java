package com.entis;

import com.entis.game.MainGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String rules = "This console chess made by Entis, and unknown github user";
    private static final String clearScreenCommand = "\033[H\033[2J";

    public static String getClearScreenCommand() {
        return clearScreenCommand;
    }

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 50; ++i) System.out.println();
            System.out.print(clearScreenCommand);
            System.out.flush();
            System.out.println("===========================");
            System.out.println("            Menu           ");
            System.out.println("       1 - new game        ");
            System.out.println("       2 - instructions    ");
            System.out.println("       3 - exit            ");
            System.out.println("===========================");
            System.out.print("Your choice: ");
            try {
                String menuChoice = reader.readLine();
                if (menuChoice.equals("1")) {
                    MainGame.main(new String[1]);
                } else {
                    if (menuChoice.equals("2")) {
                        System.out.println(rules);
                        System.out.println("Press any button");
                        reader.readLine();
                        throw new Exception("");
                    } else {
                        if (menuChoice.equals("3")) {
                            System.out.println("Thanks for playing");
                            break;
                        } else {
                            throw new Exception("Incorrect input");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                for (int i = 0; i < 50; ++i) System.out.println();
                System.out.print(clearScreenCommand);
                System.out.flush();
                if (e.getMessage().equals("Incorrect input"))
                    System.out.println("Choose correct menu number (1, 2, 3)");
            }
        }
    }
}
