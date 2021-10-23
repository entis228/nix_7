package com.entis.controller;

import com.entis.service.CountSimpleNumbersThreads;
import com.entis.service.ReverseOutputThreads;

import java.util.List;

public class ThreadTasksController {

    public void getReverseOutputThreadsNames() {
        ReverseOutputThreads task1 = new ReverseOutputThreads();
        task1.print();
    }

    public void countSimpleNumbersInList() {
        CountSimpleNumbersThreads task2 = new CountSimpleNumbersThreads();
        List<Integer> task2Numbers = task2.getNumbers();
        System.out.println(task2Numbers);
        System.out.printf("Task 2 result = %d%n", task2.countSimpleNumbers());
    }
}
