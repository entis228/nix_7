package com.entis.game.console;

import com.entis.Main;
import com.entis.game.core.Board;
import com.entis.game.core.DisplayDriver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GameOfLife {

    public static double cellDensity = 0.3;

    public static void main(String[] args) throws Exception {
        System.out.print("Please enter number of iterations to run: ");
        Scanner in = new Scanner(System.in);
        int iterations = in.nextInt();
        System.out.println("Enter width of the board");
        int width = in.nextInt();
        System.out.println("Enter height of the board");
        int height = in.nextInt();
        if (width < 2 || height < 2) {
            throw new Exception("error size");
        }
        DisplayDriver dd = Display.getDriver();
        Board b = new Board(height, width, cellDensity);
        for (int i = 0; i <= iterations; i++) {
            dd.displayBoard(b);
            b.update();
            Thread.sleep(300);
            if (i == iterations) continue;
            Main.clearScreen();
        }
        System.out.println("Press any button");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
    }
}
