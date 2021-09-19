package com.entis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DistinctNameService {

    private final List<String> names=new ArrayList<>();

    public void addName(String name){
        names.add(name);
    }

    public String getDistinct(){
        try {
            String name=names.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() == 1)
                    .map(Map.Entry::getKey).findFirst().get();
            names.clear();
            return name;
        }catch (NoSuchElementException e){
            return "Distinct name not found";
        }
    }
}
