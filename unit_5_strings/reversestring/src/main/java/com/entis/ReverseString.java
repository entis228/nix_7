package com.entis;

import java.util.Arrays;

public class ReverseString {

    public static String reverse(String src, boolean reverseWords) {
        if (reverseWords) {
            String[] words = src.split("\\s");
            for (int i = 0; i < words.length; i++) {
                words[i] = reverse(words[i], false);
            }
            return Arrays.toString(words).replaceAll("[,\\[\\]]", "");
        } else {
            char[] chars = src.toCharArray(), result = new char[src.length()];
            int indexResult = 0;
            for (int i = chars.length - 1; i >= 0; i--) {
                result[indexResult] = chars[i];
                indexResult++;
            }
            return String.valueOf(result);
        }
    }

    public static String reverse(String src, String dest) {
        if (src.contains(dest)) {
            return src.replace(dest, reverse(dest, false));
        }
        return null;
    }

    public static String reverse(String src, int firstIndex, int lastIndex) {
        String substring = src.substring(firstIndex, lastIndex);
        return src.replace(substring, reverse(substring, true));
    }
}
