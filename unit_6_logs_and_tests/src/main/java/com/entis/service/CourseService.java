package com.entis.service;

import com.entis.dao.InMemoryCourseDao;
import com.entis.data.Course;
import com.entis.data.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class CourseService {

    private static final InMemoryCourseDao dao = new InMemoryCourseDao();
    private static final StudentService studentService = new StudentService();
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public String create(Course course) {
        if (isCourseValid(course)) {
            dao.create(course);
            LOGGER_INFO.info("A new course was created. ID:" + dao.findAllCourses().get(dao.findAllCourses().size() - 1).getId());
            return "Created";
        } else return "params of course are invalid";
    }

    public String update(Course course) {
        if (isCourseValid(course)) {
            try {
                dao.update(course);
                return "success";
            } catch (NoSuchElementException e) {
                return "id of course not found";
            }
        }
        return "invalid course";
    }

    public String delete(String id) {
        try {
            dao.delete(id);
            LOGGER_WARN.warn("Course was deleted. ID: " + id);
            return "Course was deleted";
        } catch (NoSuchElementException e) {
            return "there no such course";
        }
    }

    public Course findById(String id) {
        try {
            Course course = dao.findById(id);
            return course;
        } catch (Exception e) {
            LOGGER_ERROR.error("can't find course by id:" + id);
            return null;
        }
    }

    public String addStudentToCourseById(String idCourse, String idStudent) {
        try {
            Course course = dao.findById(idCourse);
            Student student = studentService.findStudentById(idStudent);
            course.getStudents().add(student);
            dao.update(course);
            LOGGER_INFO.info("Added student " + idStudent + " to course " + idCourse);
            return "success adding";
        } catch (NoSuchElementException e) {
            LOGGER_ERROR.error("Can't add student to course because " + e.getMessage());
            return e.getMessage();
        }
    }

    public String removeStudentFromCourseById(String idCourse, String idStudent) {
        try {
            Course course = dao.findById(idCourse);
            Student student = studentService.findStudentById(idStudent);
            course.getStudents().remove(student);
            dao.update(course);
            course = dao.findById(idCourse);
            LOGGER_WARN.warn("Removed student " + idStudent + " from course " + idCourse);
            List<Course> lst = dao.findAllCourses();
            return "success removing";
        } catch (NoSuchElementException e) {
            LOGGER_ERROR.error("Can't remove student from course because " + e.getMessage());
            return e.getMessage();
        }
    }

    public List<Course> findAll() {
        return dao.findAllCourses();
    }

    private boolean isCourseValid(Course course) {
        List<Student> students = studentService.findAllStudents();
        String regex = "[\\w\\s]{4,}";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(course.getName()).matches()) {
            return course.getStudents().stream().allMatch(x -> students.stream().anyMatch(y -> y.getId().equals(x.getId())));
        }
        return false;
    }
}
