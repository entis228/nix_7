package com.entis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter your string with brackets");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Result: " + Brackets.areBracketsInStringBalanced(reader.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
