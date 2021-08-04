package com.entis;

import com.entis.horse.InfinityChessHorse;
import com.entis.triangle.Point;
import com.entis.triangle.TriangleArea;
import com.entis.unicnumbers.UniqueNumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Level1.\n" +
                "Choose the function.\n" +
                "1-Unics numbers in string\n" +
                "2-Triangle area\n" +
                "3-Horse on the chessboard");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = reader.readLine();
            switch (input) {
                case "1":
                    System.out.println("Enter the numbers divided by empty field (e.g 1 2 3 4)");
                    String[] rawNums = reader.readLine().split("\\s+");
                    int[] nums = new int[rawNums.length];
                    for (int i = 0; i < nums.length; i++) {
                        nums[i] = Integer.parseInt(rawNums[i]);
                    }
                    System.out.println("Count of unique numbers is " + UniqueNumbers.getUniqueCount(nums));
                    break;
                case "2":
                    ArrayList<Point> points = new ArrayList<>();
                    for (int i = 1; i < 4; i++) {
                        int coordX, coordY;
                        System.out.println("Enter point " + i + " X");
                        coordX = Integer.parseInt(reader.readLine());
                        System.out.println("Enter point " + i + " Y");
                        coordY = Integer.parseInt(reader.readLine());
                        points.add(new Point(coordX, coordY));
                    }
                    System.out.println("Your area is " + TriangleArea.getArea(points.get(0), points.get(1), points.get(2)));
                    break;
                case "3":
                    System.out.println("Enter the position of horse on the chessboard (e.g D3)");
                    String horseCoords = reader.readLine();
                    System.out.println("Enter the destination position of horse on the chessboard (e.g D3)");
                    String destinationCoords = reader.readLine();
                    System.out.println("Result: " + InfinityChessHorse.isCanMove(horseCoords, destinationCoords));
                    break;
                default:
                    System.out.println("Incorrect input");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
