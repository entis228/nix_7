package com.entis.facade;

import com.entis.data.Student;
import com.entis.service.StudentService;

import java.util.List;

public class StudentFacade {

    StudentService service = new StudentService();

    public String create(String fullName, int age, String phone) {
        Student student = new Student();
        student.setFullName(fullName);
        student.setAge(age);
        student.setPhoneNumber(phone);
        return service.create(student);
    }

    public String update(String id, String fullName, int age, String phone) {
        Student student = new Student();
        student.setId(id);
        student.setFullName(fullName);
        student.setAge(age);
        student.setPhoneNumber(phone);
        return service.update(student);
    }

    public String delete(String id) {
        return service.delete(id);
    }

    public List<Student> findAll() {
        return service.findAllStudents();
    }

    public String findByID(String id) {
        Student student = service.findStudentById(id);
        if (student == null) {
            return "Not found";
        }
        return student.toString();
    }
}
