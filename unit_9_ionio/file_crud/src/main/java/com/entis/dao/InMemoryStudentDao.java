package com.entis.dao;

import com.entis.dao.interfaces.StudentDao;
import com.entis.data.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryStudentDao implements StudentDao {

    private static final String dbPath = "db";
    private static final String filePath = dbPath + File.separator + "students.json";
    private List<Student> students = findAllStudents();
    private static final Gson gson=new GsonBuilder().setPrettyPrinting().create();

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
        try {
            List<Student>res= gson.fromJson(new FileReader(filePath),new TypeToken<List<Student>>(){}.getType());
            if(res==null)throw new FileNotFoundException();
            return res;
        } catch (FileNotFoundException e) {
            ArrayList<Student> res = new ArrayList<>();
            saveAllStudents(res);
            return res;
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
        try (BufferedWriter writer=new BufferedWriter(new FileWriter(filePath))){
            String json = gson.toJson(lst);
            writer.write(json);
        } catch (IOException e) {
            if(e instanceof FileNotFoundException){
                initDBFolder();
                saveAllStudents(new ArrayList<>());
            }else
            e.printStackTrace();
        }
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (students.stream().anyMatch(student -> student.getId().equals(id))) {
            return generateId();
        }
        return id;
    }


}
