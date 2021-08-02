package com.entis.testlib;

import com.entis.ReverseString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String clearScreenCommand = "\033[H\033[2J";
    private static String inputString;

    public static void main(String[] args) {
        System.out.println("Enter your string:");
        try {
            inputString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                System.out.println("Your current string is: " + inputString);
                System.out.println("Choose the option (1-usual reverse, 2-reverse substring, 3-reverse substring within two indexes, 4-edit the string, 0-exit)");
                String result = "";
                boolean showResult = false;
                switch (reader.readLine()) {
                    case "1":
                        System.out.println("Enter param to reverse all string or each word(true/false)");
                        boolean par2 = Boolean.getBoolean(reader.readLine());
                        result = ReverseString.reverse(inputString, par2);
                        showResult = true;
                        break;
                    case "2":
                        System.out.println("Enter the substring, if substring is missing in your main string, the result will be null");
                        String substring = reader.readLine();
                        result = ReverseString.reverse(inputString, substring);
                        showResult = true;
                        break;
                    case "3":
                        try {
                            System.out.println("Enter your first index");
                            int index1 = Integer.parseInt(reader.readLine());
                            System.out.println("Enter your second index");
                            int index2 = Integer.parseInt(reader.readLine());
                            result = ReverseString.reverse(inputString, index1, index2);
                            showResult = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect index");
                        }
                        break;
                    case "4":
                        System.out.println("Enter your string:");
                        inputString = reader.readLine();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Enter the correct number");
                        break;
                }
                if (showResult) {
                    System.out.println("Your result is:\n" + result);
                    System.out.println("Press any key to continue");
                    reader.readLine();
                }
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
                System.out.println(clearScreenCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
