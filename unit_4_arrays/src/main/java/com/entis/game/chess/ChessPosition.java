package com.entis.game.chess;

import com.entis.game.boardgame.Position;
import com.entis.game.chess.exceptions.ChessException;

public class ChessPosition {

    private final char column;
    private final Integer row;

    public ChessPosition(char column, Integer row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instantiating position on board. Valid values are from a1 to h8.");
        }
        this.column = column;
        this.row = row;
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }

    public char getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    protected Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
