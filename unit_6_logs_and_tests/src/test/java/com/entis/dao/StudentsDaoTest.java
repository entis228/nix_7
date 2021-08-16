package com.entis.dao;

import com.entis.data.Student;
import com.entis.util.StudentUtils;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentsDaoTest {

    private static final InMemoryStudentDao studentDao = new InMemoryStudentDao();
    private static List<Student> beforeState = null;
    private static boolean isCouldBeSaved = false;

    private static void saveState() {
        if (isCouldBeSaved)
            beforeState = studentDao.findAllStudents();
    }

    @AfterAll
    private static void returnState() {
        if (isCouldBeSaved) {
            studentDao.clear();
            for (Student s : beforeState) {
                studentDao.create(s);
            }
        }
    }

    private boolean isUniqueIds(List<Student> lst) {
        for (int i = 0; i < lst.size(); i++) {
            String id = lst.get(i).getId();
            for (int j = i + 1; j < lst.size(); j++) {
                if (lst.get(j).getId().equals(id)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    @Order(1)
    public void getAll() {
        List<Student> students = studentDao.findAllStudents();
        Assertions.assertNotEquals(null, students);
        isCouldBeSaved = true;
    }

    @Test
    @Order(2)
    public void createAndClear() {
        saveState();
        for (int i = 0; i < 10; i++)
            studentDao.create(StudentUtils.generateRandom());
        List<Student> testList = studentDao.findAllStudents();
        studentDao.create(StudentUtils.generateRandom());
        List<Student> listWithStudent = studentDao.findAllStudents();
        Assertions.assertEquals(testList.size() + 1, listWithStudent.size());
        Assertions.assertTrue(isUniqueIds(listWithStudent));
        studentDao.clear();
        for (int i = 0; i < 100; i++)
            studentDao.create(StudentUtils.generateRandom());
        studentDao.clear();
        List<Student> empty = studentDao.findAllStudents();
        Assertions.assertEquals(0, empty.size());
    }

    @Test
    @Order(3)
    public void getById() {
        studentDao.clear();
        studentDao.create(StudentUtils.generateSimple());
        for (int i = 0; i < 10; i++)
            studentDao.create(StudentUtils.generateRandom());
        List<Student> testState = studentDao.findAllStudents();
        Student inList = testState.get(0);
        Student expected = studentDao.findById(inList.getId());
        List<Student> newState = studentDao.findAllStudents();
        Assertions.assertEquals(testState.size(), newState.size());
        Assertions.assertEquals(inList.getId(), expected.getId());
        Assertions.assertEquals(inList.getFullName(), expected.getFullName());
        Assertions.assertEquals(inList.getAge(), expected.getAge());
        Assertions.assertEquals(inList.getPhoneNumber(), expected.getPhoneNumber());
        Assertions.assertTrue(isUniqueIds(newState));
    }

    @Test
    @Order(4)
    public void update() {
        List<Student> lastState = studentDao.findAllStudents();
        Student oldStudent = lastState.get(0);
        Student newStudent = StudentUtils.generateRandom();
        newStudent.setId(oldStudent.getId());
        studentDao.update(newStudent);
        oldStudent = studentDao.findById(newStudent.getId());
        List<Student> newState = studentDao.findAllStudents();
        Assertions.assertEquals(lastState.size(), newState.size());
        Assertions.assertEquals(newStudent.getId(), oldStudent.getId());
        Assertions.assertEquals(newStudent.getFullName(), oldStudent.getFullName());
        Assertions.assertEquals(newStudent.getAge(), oldStudent.getAge());
        Assertions.assertEquals(newStudent.getPhoneNumber(), oldStudent.getPhoneNumber());
        Assertions.assertTrue(isUniqueIds(newState));
    }

    @Test
    @Order(5)
    public void delete() {
        studentDao.clear();
        for (int i = 0; i < 10; i++)
            studentDao.create(StudentUtils.generateRandom());
        List<Student> dbList = studentDao.findAllStudents();
        Student student = dbList.get(dbList.size() - 1);
        studentDao.delete(student.getId());
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> studentDao.findById(student.getId()));
        Assertions.assertNotNull(exception);
        List<Student> newState = studentDao.findAllStudents();
        Assertions.assertEquals(dbList.size() - 1, newState.size());
        for (Student s : newState) {
            Assertions.assertNotEquals(s.getId(), student.getId());
        }
        Assertions.assertTrue(isUniqueIds(newState));
    }
}
