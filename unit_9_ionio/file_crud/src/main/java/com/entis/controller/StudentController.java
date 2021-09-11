package com.entis.controller;

import com.entis.data.Student;
import com.entis.facade.StudentFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StudentController {

    StudentFacade facade = new StudentFacade();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void create() {
        try {
            System.out.println("Enter the full name of student e.g Name Surname Lastname");
            String name = reader.readLine();
            System.out.println("Enter age (should be greater than 14)");
            int age = Integer.parseInt(reader.readLine());
            System.out.println("Enter phone e.g 0682282281 or +380682282281");
            String phone = reader.readLine();
            System.out.println(facade.create(name, age, phone));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findAll() {
        List<Student> students = facade.findAll();
        if (students.isEmpty()) {
            System.out.println("list is empty");
        } else
            students.forEach(System.out::println);
    }

    public void findById() {
        try {
            System.out.println("Enter id of student");
            String id = reader.readLine();
            System.out.println(facade.findByID(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            System.out.println("Enter id of student which will be updated");
            String id = reader.readLine();
            System.out.println("Enter the full name of student");
            String name = reader.readLine();
            System.out.println("Enter age (should be greater than 14)");
            int age = Integer.parseInt(reader.readLine());
            System.out.println("Enter phone");
            String phone = reader.readLine();
            System.out.println(facade.update(id, name, age, phone));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            System.out.println("Enter id of student which will be deleted");
            String id = reader.readLine();
            System.out.println(facade.delete(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
