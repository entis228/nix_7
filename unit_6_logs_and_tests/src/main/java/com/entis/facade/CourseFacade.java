package com.entis.facade;

import com.entis.data.Course;
import com.entis.data.Student;
import com.entis.service.CourseService;
import com.entis.service.StudentService;

import java.util.ArrayList;
import java.util.List;

public class CourseFacade {

    CourseService courseService = new CourseService();
    StudentService studentService = new StudentService();

    public String create(String name, List<String> ids) {
        List<Student> inDbStudents = studentService.findAllStudents();
        boolean allIdsContains = ids.stream().allMatch(x -> inDbStudents.stream().anyMatch(y -> y.getId().equals(x)));
        if (!allIdsContains) {
            return "missing id of some student in database";
        }
        List<Student> neededStudents = new ArrayList<>();
        for (String s : ids) {
            neededStudents.add(inDbStudents.stream().filter(x -> x.getId().equals(s)).findFirst().get());
        }
        Course course = new Course();
        course.setName(name);
        course.setStudents(neededStudents);
        return courseService.create(course);
    }

    public String update(String idCourse, String name, List<String> idStudents) {
        List<Student> inDbStudents = studentService.findAllStudents();
        boolean allIdsContains = idStudents.stream().allMatch(x -> inDbStudents.stream().anyMatch(y -> y.getId().equals(x)));
        if (!allIdsContains) {
            return "missing id of some student in database";
        }
        List<Student> neededStudents = new ArrayList<>();
        for (String s : idStudents) {
            neededStudents.add(inDbStudents.stream().filter(x -> x.getId().equals(s)).findFirst().get());
        }
        Course course = new Course();
        course.setName(name);
        course.setStudents(neededStudents);
        course.setId(idCourse);
        return courseService.update(course);
    }

    public String findById(String id) {
        return courseService.findById(id).toString();
    }

    public String delete(String id) {
        return courseService.delete(id);
    }

    public List<Course> findAll() {
        return courseService.findAll();
    }

    public String addStudentToCourseById(String idCourse, String idStudent) {
        return courseService.addStudentToCourseById(idCourse, idStudent);
    }

    public String removeStudentFromCourseById(String idCourse, String idStudent) {
        return courseService.removeStudentFromCourseById(idCourse, idStudent);
    }
}