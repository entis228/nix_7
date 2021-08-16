package com.entis.dao;

import com.entis.data.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryStudentDao {

    private static final String dbPath = (System.getProperty("user.dir") + "/db").replaceAll("/", File.separator);
    private static final String filePath = dbPath + File.separator + "students.db";
    private List<Student> students = findAllStudents();

    public void create(Student student) {
        students=findAllStudents();
        student.setId(generateId());
        students.add(student);
        saveAllStudents(students);
    }

    public Student findById(String id) {
        students = findAllStudents();
        return students.stream().filter(student -> student.getId().equals(id)).findFirst().get();
    }

    public void update(Student student) {
        students=findAllStudents();
        Student inDbUser = findById(student.getId());
        inDbUser.setAge(student.getAge());
        inDbUser.setFullName(student.getFullName());
        inDbUser.setPhoneNumber(student.getPhoneNumber());
        saveAllStudents(students);
    }

    public void delete(String id) {
        students=findAllStudents();
        students.removeIf(student -> student.getId().equals(id));
        saveAllStudents(students);
    }

    public List<Student> findAllStudents() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Student>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            ArrayList<Student> res = new ArrayList<>();
            saveAllStudents(res);
            return res;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clear() {
        saveAllStudents(new ArrayList<>());
    }

    private void initDBFolder() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveAllStudents(List<Student> lst) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(lst);
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                initDBFolder();
                saveAllStudents(lst);
            } else e.printStackTrace();
        }
        students = lst;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (students.stream().anyMatch(student -> student.getId().equals(id))) {
            return generateId();
        }
        return id;
    }


}
