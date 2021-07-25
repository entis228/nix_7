package com.entis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.TreeMap;

public class Second {

    public void doTask() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Second task. Enter your string");
        String input = reader.readLine();
        String[]letters=input.split("[^a-zA-Z]?");
        TreeMap<String,Integer> map=new TreeMap<>();
        Arrays.stream(letters).filter(x->!x.equals("")).forEach(x-> {
            if (map.containsKey(x)) {
                map.replace(x, map.get(x) + 1);
            } else {
                map.put(x, 1);
            }
        });
        System.out.println("Your letters with count:");
        map.forEach((key,value)->System.out.println(key+" - "+value));
    }
}
