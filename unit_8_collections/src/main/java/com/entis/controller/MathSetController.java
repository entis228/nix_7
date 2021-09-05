package com.entis.controller;

import com.entis.entity.MathSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MathSetController {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private MathSet mathSet;
    private boolean isDoubleType = false;

    private void printSet() {
        try {
            isSetInitialized();
            System.out.println("Result:");
            if(mathSet.getLength()==0)
                System.out.println("Set is empty");
            else
            System.out.println(mathSet);
        }catch (NullPointerException e){
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
    }

    void initializeMathSet() throws IOException {
        try {
            System.out.println("Select type of numbers(1-integer, 2-floating point)");
            int input = Integer.parseInt(reader.readLine());
            if (input != 1 && input != 2) throw new NumberFormatException();
            if (input == 1) {
                mathSet = new MathSet<Integer>();
                isDoubleType = false;
            } else {
                mathSet = new MathSet<Double>();
                isDoubleType = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input");
        }
    }

    private void isSetInitialized() {
        if (mathSet == null) throw new NullPointerException();
    }

    void add() {
        try {
            isSetInitialized();
            System.out.println("Enter the value");
            String input = reader.readLine();
            if (isDoubleType) {
                mathSet.add(Double.parseDouble(input));
            } else {
                mathSet.add(Integer.parseInt(input));
            }
            printSet();
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void join() {
        try {
            isSetInitialized();
            MathSet joinedSet;
            if (isDoubleType) {
                joinedSet = new MathSet<Double>();
            } else {
                joinedSet = new MathSet<Integer>();
            }
            System.out.println("Enter the list which will be joined (type number and press enter) if you want to stop typing, type stop");
            while (true) {
                String input = reader.readLine();
                if (input.equals("stop")) break;
                if (isDoubleType) {
                    joinedSet.add(Double.parseDouble(input));
                } else {
                    joinedSet.add(Integer.parseInt(input));
                }
            }
            mathSet.join(joinedSet);
            printSet();
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void intersection() {
        try {
            isSetInitialized();
            MathSet interSet;
            if (isDoubleType) {
                interSet = new MathSet<Double>();
            } else {
                interSet = new MathSet<Integer>();
            }
            System.out.println("Enter the list which will intersection (type number and press enter) if you want to stop typing, type stop");
            while (true) {
                String input = reader.readLine();
                if (input.equals("stop")) break;
                if (isDoubleType) {
                    interSet.add(Double.parseDouble(input));
                } else {
                    interSet.add(Integer.parseInt(input));
                }
            }
            mathSet.intersection(interSet);
            printSet();
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sortDesc() {
        try {
            isSetInitialized();
            System.out.println("Choose type of sorting(1-common, 2-indexed, 3-around value)");
            int input = Integer.parseInt(reader.readLine());
            if (input != 1 && input != 2 && input != 3) throw new NumberFormatException();
            System.out.println("Before");
            printSet();
            if (input == 1)
                mathSet.sortDesc();
            else {
                if (input == 2) {
                    System.out.println("Enter the first index");
                    int index1 = Integer.parseInt(reader.readLine());
                    System.out.println("Enter the second index");
                    int index2 = Integer.parseInt(reader.readLine());
                    mathSet.sortDesc(index1, index2);
                } else {
                    System.out.println("Enter the value");
                    Number value;
                    if (isDoubleType) {
                        value = Double.parseDouble(reader.readLine());
                    } else {
                        value = Integer.parseInt(reader.readLine());
                    }
                    mathSet.sortDesc(value);
                }
            }
            System.out.println("After");
            printSet();
        } catch (NumberFormatException e) {
            System.out.println("incorrect input");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
    }

    void sortAsc() {
        try {
            isSetInitialized();
            System.out.println("Choose type of sorting(1-common, 2-indexed, 3-around value)");
            int input = Integer.parseInt(reader.readLine());
            if (input != 1 && input != 2 && input != 3) throw new NumberFormatException();
            System.out.println("Before");
            printSet();
            if (input == 1)
                mathSet.sortAsc();
            else {
                if (input == 2) {
                    System.out.println("Enter the first index");
                    int index1 = Integer.parseInt(reader.readLine());
                    System.out.println("Enter the second index");
                    int index2 = Integer.parseInt(reader.readLine());
                    mathSet.sortAsc(index1, index2);
                } else {
                    System.out.println("Enter the value");
                    Number value;
                    if (isDoubleType) {
                        value = Double.parseDouble(reader.readLine());
                    } else {
                        value = Integer.parseInt(reader.readLine());
                    }
                    mathSet.sortAsc(value);
                }
            }
            System.out.println("After");
            printSet();
        } catch (NumberFormatException e) {
            System.out.println("incorrect input");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
    }

    void get() {
        try {
            isSetInitialized();
            System.out.println("Enter the index of element");
            int index = Integer.parseInt(reader.readLine());
            Number number = mathSet.get(index);
            System.out.println("Result:");
            System.out.println(number.toString());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect input");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
    }

    void getMax() {
        try {
            isSetInitialized();
            System.out.println("Maximal value: " + mathSet.getMax());
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("List is empty");
        }
    }

    void getMin() {
        try {
            isSetInitialized();
            System.out.println("Minimal value: " + mathSet.getMin());
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("List is empty");
        }
    }

    void getAverage() {
        try {
            isSetInitialized();
            System.out.println("Average value: " + mathSet.getAverage());
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("List is empty");
        }
    }

    void getMedian() {
        try {
            isSetInitialized();
            System.out.println("Median: " + mathSet.getMedian());
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("List is empty");
        }
    }

    void cut() {
        try{
            isSetInitialized();
            System.out.println("Enter the first index");
            int index1 = Integer.parseInt(reader.readLine());
            System.out.println("Enter the second index");
            int index2 = Integer.parseInt(reader.readLine());
            System.out.println("Before:");
            printSet();
            MathSet result=mathSet.cut(index1,index2);
            System.out.println("After:");
            System.out.println(result);
        }catch (NullPointerException e){
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e){
            System.out.println("Incorrect input");
        }
    }

    void clear() {
        try {
            isSetInitialized();
            mathSet.clear();
            System.out.println("Cleared");
        } catch (NullPointerException e) {
            System.out.println("MathSet is not initialized, use InitializeMathSet first");
        }
    }
}
