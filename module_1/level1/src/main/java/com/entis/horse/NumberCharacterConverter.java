package com.entis.horse;

public class NumberCharacterConverter {

    private static final int SymbolShift = 64;

    public static int convert(char symbol) {
        return symbol - SymbolShift;
    }

    public static char convert(int digit) {
        return (char) (digit + SymbolShift);
    }
}
