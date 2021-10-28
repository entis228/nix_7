package com.entis.controller;

import com.entis.entity.Lesson;
import com.entis.service.LessonService;
import com.entis.service.impl.NearestLessonService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class LessonController {

    public void getNearestLesson(String[] args) {
        try (LessonService service = new NearestLessonService(args[0], args[1]);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter user id");
            Long id = Long.parseLong(reader.readLine());
            List<Lesson> nearest = service.getLessons(id);
            nearest.forEach(System.out::println);
            System.out.println("Press enter");
            reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
