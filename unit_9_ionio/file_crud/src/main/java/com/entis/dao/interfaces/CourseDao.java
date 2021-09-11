package com.entis.dao.interfaces;

import com.entis.data.Course;

import java.util.List;

public interface CourseDao {

    void create(Course course);

    List<Course> findAllCourses();

    Course findById(String id);

    void update(Course course);

    void delete(String id);
}
