package com.entis.util;

import com.entis.data.Course;
import com.entis.data.Student;

import java.util.ArrayList;
import java.util.List;

public class CourseUtils {

    private static final String[] names = {"STM32", "Arduino", "Raspberry Pi", "Java", "Android", "Frontend", "C++", ".NET", "Gamedev"};

    public static int getRandomNumber(int from, int to) {
        return (int) (Math.random() * (to - from + 1) + from);
    }

    public static Course generateSimple() {
        Course course = new Course();
        course.setName("Fullstack evangelion developer");
        List<Student> students = new ArrayList<>();
        Student boy = new Student();
        boy.setAge(18);
        boy.setFullName("Иванов Иван Иванович");
        boy.setPhoneNumber("0682282281");
        Student girl = new Student();
        girl.setPhoneNumber("0971221225");
        girl.setFullName("Сорью Асочка Лэнгли");
        girl.setAge(20);
        students.add(boy);
        students.add(girl);
        return course;
    }

    public static Course generateRandom() {
        List<Student> students = new ArrayList<>();
        Course course = new Course();
        course.setName(names[getRandomNumber(0, names.length - 1)]);
        for (int i = 0; i < getRandomNumber(5, 15); i++) {
            students.add(StudentUtils.generateRandom());
        }
        course.setStudents(students);
        return course;
    }
}
