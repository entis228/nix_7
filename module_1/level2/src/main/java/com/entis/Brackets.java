package com.entis;

import java.util.Stack;

public class Brackets {

    private static boolean isTwoBracketsBalanced(String bracket1, String bracket2) {
        return bracket1.equals("{") && bracket2.equals("}") || bracket1.equals("[") && bracket2.equals("]") || bracket1.equals("(") && bracket2.equals(")");
    }

    public static boolean areBracketsInStringBalanced(String expr) {
        if (expr == null) return false;
        if (expr.isBlank()) return true;
        Stack<String> stack = new Stack<>();
        String[] rawBrackets = expr.split("[^\\[{(\\])}]");
        String openBrackets = "{([";
        StringBuilder builder = new StringBuilder();
        for (String s : rawBrackets) {
            builder.append(s);
        }
        String[] brackets = builder.toString().split("");
        for (String bracket : brackets) {
            if (openBrackets.contains(bracket)) {
                stack.push(bracket);
            } else {
                String lastBracket = stack.pop();
                if (!isTwoBracketsBalanced(lastBracket, bracket))
                    return false;
            }
        }
        return true;
    }
}
