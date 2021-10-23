package com.entis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CountSimpleNumbersThreads {

    private volatile Integer result;

    private final List<Integer> numbers;

    public CountSimpleNumbersThreads(List<Integer> lst) {
        numbers = lst;
        result = 0;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public CountSimpleNumbersThreads() {
        numbers = new ArrayList<>();
        generateNumbers();
        result = 0;
    }

    public int countSimpleNumbers() {
        int dividerIndex = numbers.size() / 2;
        new Thread(new Counter(0, dividerIndex)).start();
        new Thread(new Counter(dividerIndex + 1, numbers.size() - 1)).start();
        return result;
    }

    public void generateNumbers() {
        for (int i = 0; i < 40; i++) {
            numbers.add(getRandomNumber(0, 200));
        }
    }

    private int getRandomNumber(int from, int to) {
        return (int) (Math.random() * (to - from + 1) + from);
    }

    private class Counter implements Runnable {

        int indexFrom;
        int indexTo;

        public Counter(int indexFrom, int indexTo) {
            this.indexFrom = indexFrom;
            this.indexTo = indexTo;
        }

        @Override
        public void run() {
            AtomicInteger incrementer = new AtomicInteger(0);
            for (int i = indexFrom; i <= indexTo; i++) {
                if (isPrime(numbers.get(i))) {
                    incrementer.set(result);
                    result = incrementer.incrementAndGet();
                }
            }
        }

        private boolean isPrime(final int number) {
            return IntStream.rangeClosed(2, number / 2).anyMatch(i -> number % i == 0);
        }
    }

}
