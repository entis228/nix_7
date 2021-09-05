package com.entis.entity;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MathSet <E extends Number & Comparable<E>> implements Iterable<Number> {

    private static final double expandedCoefficient = 1.5d;
    private static final int standardCapacity = 10;
    private int length;
    private E[] numbers;

    public MathSet() {
        numbers = (E[]) new Number[standardCapacity];
        length = 0;
    }

    public MathSet(int capacity) {
        numbers = (E[]) new Number[capacity];
        length = 0;
    }

    public MathSet(E[] numbers) {
        this.numbers = (E[]) new Number[numbers.length];
        this.length = 0;
        for (E number : numbers) {
            if (notContains(number)) {
                this.numbers[length] = number;
                length++;
            }
        }
        length = numbers.length;
    }

    public MathSet(E[]... numbers) {
        this.numbers = (E[]) new Number[standardCapacity*2];
        length = 0;
        for (E[] numberArray : numbers) {
            add(numberArray);
        }
    }

    public MathSet(MathSet<E> numbers) {
        this.numbers = numbers.numbers;
        this.length = numbers.length;
    }

    public MathSet(MathSet<E>... numbers) {
        this();
        for(MathSet<E> nums: numbers){
            for(int i=0;i< nums.getLength();i++){
                add(nums.get(i));
            }
        }
    }

    private void quickSort(E[] array, int low, int high) {
        array=numbers;
        if (array.length == 0)
            return;
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        E stand = array[middle];
        int i = low, j = high;
        while (i <= j) {
            while (array[i].compareTo(stand) < 0) {
                i++;
            }
            while (array[j].compareTo(stand) > 0) {
                j--;
            }
            if (i <= j) {
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(array, low, j);
        if (high > i)
            quickSort(array, i, high);
    }

    public int getLength() {
        return this.length;
    }

    private boolean notContains(E number) {
        return !arrayContains(numbers,length,number);
    }

    private boolean arrayContains(E[] array,int length, E number){
        for (int i = 0; i < length; i++) {
            if (array[i].equals(number)) return true;
        }
        return false;
    }

    public boolean contains(E number){
        return !notContains(number);
    }

    public void add(E n) {
        while (true) {
            if (length + 1 <= this.numbers.length) {
                if (notContains(n)) {
                    numbers[length] = n;
                    length++;
                }
                break;
            } else {
                E[] newArray = (E[]) new Number[(int) (length * expandedCoefficient)];
                System.arraycopy(numbers, 0, newArray, 0, length);
                numbers = newArray;
            }
        }
    }

    public void add(E... n) {
        for (E number : n) {
            add(number);
        }
    }

    public void join(MathSet<E> ms) {
        for (int i = 0; i < ms.getLength(); i++) {
            add(ms.numbers[i]);
        }
    }

    public void join(MathSet<E>... ms) {
        for (MathSet<E> m : ms) {
            join(m);
        }
    }

    public void sortDesc() {
        sortAsc();
        reverse(0,length-1);
    }

    private void reverse(int from, int to){
        E[]newArray= (E[]) new Number[numbers.length];
        System.arraycopy(numbers, 0, newArray, 0, length);
        int counter=from;
        for(int i=to;i>=from;i--){
            newArray[counter]=numbers[i];
            counter++;
        }
        numbers=newArray;
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        sortAsc(firstIndex,lastIndex);
        reverse(firstIndex,lastIndex);
    }

    public void sortDesc(E value) {
        int index=indexOf(value);
        sortDesc(0,index-1);
        sortDesc(index+1,length-1);
    }

    public void sortAsc() {
        quickSort(numbers,0,length-1);
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        if(firstIndex>length-1||firstIndex>lastIndex||lastIndex>length-1) throw new ArrayIndexOutOfBoundsException();
        quickSort(numbers,firstIndex,lastIndex);
    }

    public void sortAsc(E value) {
        int index=indexOf(value);
        sortAsc(0,index-1);
        sortAsc(index+1,length-1);
    }

    private int indexOf(E value){
        for(int i=0;i<length;i++){
            if(numbers[i].equals(value)){
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    public E get(int index) {
        if (index > length - 1) throw new ArrayIndexOutOfBoundsException();
        return numbers[index];
    }

    public E getMax() {
        E max=get(0);
        for(int i=1;i<length;i++){
            if(numbers[i].compareTo(max) > 0){
                max=numbers[i];
            }
        }
        return max;
    }

    public E getMin() {
        E min=get(0);
        for(int i=1;i<length;i++){
            if(numbers[i].compareTo(min) < 0){
                min=numbers[i];
            }
        }
        return min;
    }

    public double getAverage() {
        BigDecimal sum=BigDecimal.valueOf(numbers[0].doubleValue());
        for (int i=1;i<length;i++){
            sum=sum.add(BigDecimal.valueOf(numbers[i].doubleValue()));
        }
        sum=sum.divide(BigDecimal.valueOf(length));
        return sum.doubleValue();
    }

    public E getMedian() {
        return get(length/2);
    }

    public E[] toArray() {
        E[] result= (E[]) new Number[length];
        System.arraycopy(numbers, 0, result, 0, length);
        return result;
    }

    public E[] toArray(int firstIndex, int lastIndex) {
        int resultLength=lastIndex-firstIndex;
        E[] result= (E[]) new Number[resultLength];
        int resultIndex=0;
        for(int i=firstIndex;i<=lastIndex;i++){
            result[resultIndex]=numbers[i];
            resultIndex++;
        }
        return result;
    }

    public void clear() {
        numbers = (E[]) new Number[standardCapacity];
        length = 0;
    }

    public void clear(E[] numbers) {
        E[]copy= (E[]) new Number[length];
        System.arraycopy(this.numbers, 0, copy, 0, length);
        clear();
        for (E e : copy) {
            if (!arrayContains(numbers, numbers.length, e)) {
                add(e);
            }
        }
    }

    public void intersection(MathSet<E> ms) {
        for(int i=0;i<length;i++){
            E[]copy= (E[]) new Number[length];
            System.arraycopy(this.numbers, 0, copy, 0, length);
            clear();
            for (E e : copy) {
                if (arrayContains(ms.numbers, ms.length, e)) {
                    add(e);
                }
            }
        }
    }

    public void intersection(MathSet<E>... ms) {
        for (MathSet<E> m : ms) {
            intersection(m);
        }
    }

    public MathSet<E> cut(int firstIndex, int lastIndex) {
        E[]array= (E[]) new Number[lastIndex-firstIndex+1];
        int arrayIterator=0;
        for(int i=firstIndex;i<=lastIndex;i++){
            array[arrayIterator]=numbers[i];
            arrayIterator++;
        }
        return new MathSet<>(array);
    }

    @Override
    public Iterator<Number> iterator() {
        return new Iterator<>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < length && numbers[currentIndex] != null;
            }

            @Override
            public Number next() {
                return numbers[currentIndex++];
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Number> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Number> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    public String toString() {
        StringBuilder result=new StringBuilder("[");
        for(int i=0;i<length;i++){
            if(i!=length-1){
                result.append(numbers[i]).append(", ");}
            else result.append(numbers[i]).append("]");
        }
        return result.toString();
    }
}
