package com.entis;

import com.entis.controller.LessonController;

public class Main {

    public static void main(String[] args) {
        LessonController controller = new LessonController();
        controller.getNearestLesson(args);
    }
}
