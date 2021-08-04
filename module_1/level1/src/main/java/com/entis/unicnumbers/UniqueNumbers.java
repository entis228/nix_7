package com.entis.unicnumbers;

import java.util.ArrayList;
import java.util.Arrays;

public class UniqueNumbers {

    public static int getUniqueCount(int[] numbers) {
        ArrayList<Integer> lst = new ArrayList<>();
        Arrays.stream(numbers).distinct().forEach(lst::add);
        return lst.size();
    }
}
