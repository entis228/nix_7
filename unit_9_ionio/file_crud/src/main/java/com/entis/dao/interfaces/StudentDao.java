package com.entis.dao.interfaces;

import com.entis.data.Student;

import java.util.List;

public interface StudentDao {

    void create(Student student);
    void update(Student student);
    void delete(String id);
    Student findById(String id);
    List<Student> findAllStudents();
}
