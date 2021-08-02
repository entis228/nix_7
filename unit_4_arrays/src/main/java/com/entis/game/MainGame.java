package com.entis.game;

import com.entis.game.chess.ChessMatch;
import com.entis.game.chess.ChessPiece;
import com.entis.game.chess.ChessPosition;
import com.entis.game.chess.exceptions.ChessException;
import com.entis.game.views.BoardView;
import com.entis.game.views.utils.BoardColors;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainGame {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captureChessPieces = new ArrayList<>();
        System.out.print(BoardColors.ANSI_YELLOW_BACKGROUND);
        System.out.print(BoardColors.ANSI_BLACK);
        while (!chessMatch.isCheckMate()) {
            try {
                System.out.print(BoardColors.ANSI_RESET);
                BoardView.clearScreen();
                BoardView.printMatch(chessMatch, captureChessPieces);
                System.out.println();
                System.out.println("If you want a draw or exit the game, type draw in the first coordinate field");
                System.out.print("Enter your coordinate (e.g e2): ");
                ChessPosition source;
                try {
                    source = BoardView.readChessPosition(sc);
                }catch (Exception e){
                    System.out.println("Draw");
                    break;
                }

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                BoardView.clearScreen();
                BoardView.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.println();
                System.out.print("Enter your second coordinate: ");
                ChessPosition target;
                try {
                    target = BoardView.readChessPosition(sc);
                }catch (Exception e){
                    System.out.println("Draw");
                    break;
                }

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if (capturedPiece != null) {
                    captureChessPieces.add(capturedPiece);
                }
                if (chessMatch.getPromoted() != null) {
                    System.out.println("Enter the letter: (A/C/T/B)");
                    String type = sc.nextLine();
                    chessMatch.replacepromotedPiece(type);
                }
            } catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        BoardView.clearScreen();
        BoardView.printMatch(chessMatch, captureChessPieces);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(BoardColors.ANSI_RESET);
        System.out.print(BoardColors.ANSI_BLACK_BACKGROUND);
    }
}
