package com.entis.controller;

import java.io.IOException;

public class MenuOrganization {

    public void students() {
        try {
            StudentController studentController = new StudentController();
            ConsoleMenu studentConsoleMenu = new ConsoleMenu(studentController, "create", "update", "delete", "findAll", "findById");
            studentConsoleMenu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void courses() {
        try {
            CourseController courseController = new CourseController();
            ConsoleMenu courseConsoleMenu = new ConsoleMenu(courseController, "create", "update", "delete", "findAll", "findById",
                    "addStudentToCourseById", "removeStudentFromCourseById");
            courseConsoleMenu.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
