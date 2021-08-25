package com.entis.service;

import com.entis.dao.InMemoryStudentDao;
import com.entis.data.Course;
import com.entis.data.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class StudentService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");
    private final InMemoryStudentDao studentDao = new InMemoryStudentDao();

    public String create(Student student) {
        String param="student";
        if (fullNameValidate(student.getFullName())) {
            if (checkedAgeCorrectly(student.getAge())) {
                if (phoneNumberValidate(student.getPhoneNumber())) {
                    studentDao.create(student);
                    LOGGER_INFO.info("A new student was created: " + student.getFullName());
                    return "the student was created";
                } else param = "phone number";
            } else param = "age";
        } else param = "fullname";
        LOGGER_ERROR.error("Student " + student.getFullName() + " does not verified at " + param);
        return "This student can't participate in the courses because " + param + " is not affordable";
    }

    public String update(Student student) {
        String param;
        if (fullNameValidate(student.getFullName())) {
            if (checkedAgeCorrectly(student.getAge())) {
                if (phoneNumberValidate(student.getPhoneNumber())) {
                    try {
                        studentDao.update(student);
                        LOGGER_INFO.info("A student was updated: " + student.getId());
                        return "The student was updated";
                    } catch (NoSuchElementException e) {
                        return "There is no such id in database";
                    }
                } else param = "phone number";
            } else param = "age";
        } else param = "fullname";
        return "Invalid param " + param;
    }

    public String delete(String id) {
        try {
            CourseService courseService = new CourseService();
            List<Course> courses = courseService.findAll();
            Student student = findStudentById(id);
            for (Course course : courses) {
                courseService.removeStudentFromCourseById(course.getId(), student.getId());
            }
            courses = courseService.findAll();
            studentDao.delete(id);
            LOGGER_WARN.warn("Student was removed. ID: " + id);
            return "removed";
        } catch (NoSuchElementException e) {
            return "there is no such student";
        }
    }

    public Student findStudentById(String id) {
        try {
            return studentDao.findById(id);
        } catch (NoSuchElementException e) {
            LOGGER_ERROR.error("Can't find student by id:" + id);
            return null;
        }
    }

    public List<Student> findAllStudents() {
        return studentDao.findAllStudents();
    }

    private boolean checkedAgeCorrectly(int age) {
        return age > 14 && age < 80;
    }

    private boolean fullNameValidate(String fullName) {
        String regexEng = "[A-Z[^a-z]].+ [A-Z[^a-z]].+ [A-Z[^a-z]].+";
        String regexRu = "[А-Я[^а-я]].+ [А-Я[^а-я]].+ [А-Я[^а-я]].+";
        Pattern patternEn = Pattern.compile(regexEng);
        Pattern patternRu = Pattern.compile(regexRu);
        return patternEn.matcher(fullName).matches() || patternRu.matcher(fullName).matches();
    }

    private boolean phoneNumberValidate(String phone) {
        String regex = "(\\+38)?0\\d{9}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(phone).matches();
    }
}
