package com.entis.dao;

import com.entis.data.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryCourseDao {

    private static final String dbPath = "db";
    private static final String filePath = dbPath + File.separator + "courses.db";
    private List<Course> courses = findAllCourses();

    public List<Course> findAllCourses() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Course>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            ArrayList<Course> res = new ArrayList<>();
            saveAllCourses(res);
            return res;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clear() {
        saveAllCourses(new ArrayList<>());
    }

    private static void initDBFolder() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveAllCourses(List<Course> lst) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(lst);
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                initDBFolder();
                saveAllCourses(lst);
            } else e.printStackTrace();
        }
        courses = lst;
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
        courses=findAllCourses();
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
