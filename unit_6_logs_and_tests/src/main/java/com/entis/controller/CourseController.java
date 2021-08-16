package com.entis.controller;

import com.entis.data.Course;
import com.entis.facade.CourseFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

    private final CourseFacade courseFacade = new CourseFacade();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void create() {
        try {
            System.out.println("Enter the course name (should contains 4+ symbols)");
            String name = reader.readLine();
            System.out.println("Enter the ids of students which will be added (type stop to stop)");
            List<String> ids = new ArrayList<>();
            while (true) {
                String input = reader.readLine();
                if (input.equals("stop"))
                    break;
                else
                    ids.add(input);
            }
            System.out.println("Your result:");
            System.out.println(courseFacade.create(name, ids));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            System.out.println("Enter the course id");
            String id = reader.readLine();
            System.out.println("Enter the new name of course");
            String name = reader.readLine();
            System.out.println("Enter the ids of students which will be added (type stop to stop)");
            List<String> ids = new ArrayList<>();
            while (true) {
                String input = reader.readLine();
                if (input.equals("stop"))
                    break;
                else
                    ids.add(input);
            }
            System.out.println("Your result:");
            System.out.println(courseFacade.update(id, name, ids));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findById() {
        try {
            System.out.println("Enter id of course");
            String id = reader.readLine();
            System.out.println(courseFacade.findById(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            System.out.println("Enter id of course to delete");
            String id = reader.readLine();
            System.out.println(courseFacade.delete(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findAll() {
        List<Course> courses = courseFacade.findAll();
        if (courses.isEmpty()) {
            System.out.println("No courses");
        } else
            courses.forEach(System.out::println);
    }

    public void addStudentToCourseById() {
        try {
            System.out.println("Enter id of course");
            String idCourse = reader.readLine();
            System.out.println("Enter id of student which will be added");
            String idStudent = reader.readLine();
            System.out.println(courseFacade.addStudentToCourseById(idCourse, idStudent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeStudentFromCourseById() {
        try {
            System.out.println("Enter id of course");
            String idCourse = reader.readLine();
            System.out.println("Enter id of student which will be removed");
            String idStudent = reader.readLine();
            System.out.println(courseFacade.removeStudentFromCourseById(idCourse, idStudent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
