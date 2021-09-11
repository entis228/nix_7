package com.entis.dao;

import com.entis.dao.interfaces.CourseDao;
import com.entis.data.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryCourseDao implements CourseDao {

    private static final String dbPath = "db";
    private static final String filePath = dbPath + File.separator + "courses.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<Course> courses = findAllCourses();

    private static void initDBFolder() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Course> findAllCourses() {
        try {
            List<Course> res = gson.fromJson(new FileReader(filePath), new TypeToken<List<Course>>() {

            }.getType());
            if (res == null) throw new FileNotFoundException();
            return res;
        } catch (FileNotFoundException e) {
            ArrayList<Course> res = new ArrayList<>();
            saveAllCourses(res);
            return res;
        }
    }

    public void clear() {
        saveAllCourses(new ArrayList<>());
    }

    private void saveAllCourses(List<Course> lst) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String json = gson.toJson(lst);
            writer.write(json);
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                initDBFolder();
                saveAllCourses(new ArrayList<>());
            } else
                e.printStackTrace();
        }
    }

    public void create(Course course) {
        course.setId(generateId());
        courses.add(course);
        saveAllCourses(courses);
    }

    public Course findById(String id) {
        courses = findAllCourses();
        return courses.stream().filter(course -> course.getId().equals(id)).findFirst().get();
    }

    public void update(Course course) {
        Course inDbUser = findById(course.getId());
        inDbUser.setStudents(course.getStudents());
        inDbUser.setName(course.getName());
        saveAllCourses(courses);
    }

    public void delete(String id) {
        courses = findAllCourses();
        courses.removeIf(course -> course.getId().equals(id));
        saveAllCourses(courses);
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (courses.stream().anyMatch(course -> course.getId().equals(id))) {
            return generateId();
        }
        return id;
    }

}
