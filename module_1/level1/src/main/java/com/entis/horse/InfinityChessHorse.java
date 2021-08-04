package com.entis.horse;

public class InfinityChessHorse {

    public static boolean isCanMove(char horseX, int horseY, char destX, int destY) {
        boolean result = false;
        int differenceX = Math.abs(NumberCharacterConverter.convert(horseX) - NumberCharacterConverter.convert(destX));
        int differenceY = Math.abs(horseY - destY);
        if (differenceX == 1 && differenceY == 2 || differenceX == 2 && differenceY == 1) {
            result = true;
        }
        return result;
    }

    private static boolean isStringCoordsNotCorrect(String coords) {
        boolean checkLength = coords.length() != 2;
        boolean isAlph = Character.isAlphabetic(coords.charAt(0));
        boolean isNum = Character.isDigit(coords.charAt(1));
        return checkLength || !(isAlph && isNum);
    }

    public static String isCanMove(String horseInput, String destinationInput) {
        if (isStringCoordsNotCorrect(horseInput) || isStringCoordsNotCorrect(destinationInput)) {
            return "Incorrect input";
        }
        if (isCanMove(Character.toUpperCase(horseInput.charAt(0)), Integer.parseInt(horseInput.substring(1)),
                Character.toUpperCase(destinationInput.charAt(0)), Integer.parseInt(destinationInput.substring(1)))) {
            return "Horse can go there";
        } else return "Horse can't go there";
    }
}
