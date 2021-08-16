package com.entis.controller;

import com.entis.dao.InMemoryCourseDao;
import com.entis.dao.InMemoryStudentDao;
import com.entis.data.Course;
import com.entis.data.Student;
import com.entis.util.CourseUtils;
import com.entis.util.StudentUtils;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoursesControllerTest {

    private static final CourseController controller = new CourseController();
    private static final InMemoryCourseDao courseDao = new InMemoryCourseDao();
    private static final InMemoryStudentDao studentDao = new InMemoryStudentDao();
    private static final ByteArrayOutputStream receiveOut = new ByteArrayOutputStream();
    private static InputStream sysIn;
    private static PrintStream sysOut;
    private static PrintStream customOutStream;
    private static List<Course> savedCoursesState;
    private static List<Student> savedStudentsDbState;
    private static List<Student> testStudents;

    private static void setCustomSystemOut() {
        try {
            customOutStream = new PrintStream(receiveOut, true, StandardCharsets.UTF_8.name());
            System.setOut(customOutStream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void clearCourseDbAndSetToSystemInputStreamValues(String input) {
        courseDao.clear();
        setToSystemInputStreamValues(input);
    }

    private static void setToSystemInputStreamValues(String input) {
        receiveOut.reset();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        controller.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private static void createDbStudents() {
        savedStudentsDbState = studentDao.findAllStudents();
        studentDao.clear();
        for (int i = 0; i < 20; i++) {
            studentDao.create(StudentUtils.generateRandom());
        }
        testStudents = studentDao.findAllStudents();
    }

    private static void resetDbStudents() {
        studentDao.clear();
        for (Student s : savedStudentsDbState) {
            studentDao.create(s);
        }
    }

    @BeforeAll
    private static void init() {
        savedCoursesState = courseDao.findAllCourses();
        courseDao.clear();
        sysOut = System.out;
        sysIn = System.in;
        setCustomSystemOut();
        createDbStudents();
    }

    @AfterAll
    private static void resetStates() {
        courseDao.clear();
        for (Course s : savedCoursesState) {
            courseDao.create(s);
        }
        customOutStream.close();
        System.setOut(sysOut);
        System.setIn(sysIn);
        resetDbStudents();
    }

    @Test
    @Order(2)
    public void createNormal() {
        StringBuilder userInput = new StringBuilder("TestCourse\n");
        for (int i = 0; i < 10; i++) {
            userInput.append(testStudents.get(i).getId()).append("\n");
        }
        userInput.append("stop\n");
        clearCourseDbAndSetToSystemInputStreamValues(userInput.toString());
        controller.create();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("Created", outputValues[3]);
        List<Course> inDBCourses = courseDao.findAllCourses();
        Course course = inDBCourses.get(0);
        Assertions.assertEquals(1, inDBCourses.size());
        Assertions.assertEquals("TestCourse", course.getName());
        Assertions.assertEquals(10, course.getStudents().size());
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals(testStudents.get(i), course.getStudents().get(i));
        }
    }

    @Test
    @Order(3)
    public void createAbnormalName() {
        StringBuilder userInput = new StringBuilder("Jar\n");
        for (int i = 0; i < 10; i++) {
            userInput.append(testStudents.get(i).getId()).append("\n");
        }
        userInput.append("stop\n");
        clearCourseDbAndSetToSystemInputStreamValues(userInput.toString());
        controller.create();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("params of course are invalid", outputValues[3]);
        Assertions.assertEquals(0, courseDao.findAllCourses().size());
    }

    @Test
    @Order(4)
    public void createAbnormalIds() {
        StringBuilder userInput = new StringBuilder("TestCourse\n");
        for (int i = 0; i < 10; i++) {
            userInput.append(testStudents.get(i).getId()).append("\n");
        }
        userInput.append("da\n");
        userInput.append("stop\n");
        clearCourseDbAndSetToSystemInputStreamValues(userInput.toString());
        controller.create();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("missing id of some student in database", outputValues[3]);
        Assertions.assertEquals(0, courseDao.findAllCourses().size());
    }

    @Test
    @Order(5)
    public void updateNormal() {
        courseDao.clear();
        List<Student> students = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            students.add(testStudents.get(i));
        }
        Course testCourse = new Course();
        testCourse.setName("TestCourse");
        testCourse.setStudents(students);
        courseDao.create(testCourse);
        testCourse = courseDao.findAllCourses().get(0);
        setToSystemInputStreamValues(testCourse.getId() + "\nJava developers\n" + testStudents.get(7).getId() + "\nstop\n");
        controller.update();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("success", outputValues[4]);
        List<Course> inDbCourses = courseDao.findAllCourses();
        Assertions.assertEquals(1, inDbCourses.size());
        Course actualCourse = inDbCourses.get(0);
        Assertions.assertEquals(testCourse.getId(), actualCourse.getId());
        Assertions.assertNotEquals(testCourse, actualCourse);
        Assertions.assertEquals(1, actualCourse.getStudents().size());
        Assertions.assertEquals(testStudents.get(7), actualCourse.getStudents().get(0));
    }

    @Test
    @Order(5)
    public void updateAbnormalId() {
        courseDao.clear();
        List<Student> students = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            students.add(testStudents.get(i));
        }
        Course testCourse = new Course();
        testCourse.setName("TestCourse");
        testCourse.setStudents(students);
        courseDao.create(testCourse);
        testCourse = courseDao.findAllCourses().get(0);
        setToSystemInputStreamValues(testCourse.getId() + "\nJava developers\nAll you had to do, was follow dam train CJ\nstop\n");
        controller.update();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("missing id of some student in database", outputValues[4]);
        List<Course> inDbCourses = courseDao.findAllCourses();
        Assertions.assertEquals(1, inDbCourses.size());
        Course actualCourse = inDbCourses.get(0);
        Assertions.assertEquals(testCourse, actualCourse);
    }

    @Test
    public void findAll() {
        receiveOut.reset();
        List<Course> inDbCourses = courseDao.findAllCourses();
        controller.findAll();
        String testOut = receiveOut.toString();
        receiveOut.reset();
        if (inDbCourses.isEmpty())
            System.out.println("No courses");
        else
            inDbCourses.forEach(System.out::println);
        Assertions.assertEquals(testOut, receiveOut.toString());
    }

    @Test
    @Order(6)
    public void findById() {
        courseDao.clear();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            List<Student> students = new ArrayList<>();
            for (int j = CourseUtils.getRandomNumber(0, 15); j < 20; j++) {
                students.add(testStudents.get(j));
            }
            course.setName("Course" + i);
            course.setStudents(students);
            courseDao.create(course);
        }
        List<Course> inDbCourses = courseDao.findAllCourses();
        Course testCourse = inDbCourses.get(7);
        setToSystemInputStreamValues(testCourse.getId());
        controller.findById();
        Assertions.assertEquals(10, courseDao.findAllCourses().size());
        String beginResult = "Enter id of course";
        Assertions.assertTrue(receiveOut.toString().startsWith(beginResult));
        String[] rawCourse = receiveOut.toString().split(beginResult);
        Assertions.assertEquals(testCourse.toString().trim(), rawCourse[1].trim());
    }

    @Test
    @Order(7)
    public void delete() {
        courseDao.clear();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            List<Student> students = new ArrayList<>();
            for (int j = CourseUtils.getRandomNumber(0, 15); j < 20; j++) {
                students.add(testStudents.get(j));
            }
            course.setName("Course" + i);
            course.setStudents(students);
            courseDao.create(course);
        }
        Course testCourse = courseDao.findAllCourses().get(0);
        setToSystemInputStreamValues(testCourse.getId());
        controller.delete();
        Assertions.assertEquals(9, courseDao.findAllCourses().size());
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals(outputValues[1], "Course was deleted");
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> courseDao.findById(testCourse.getId()));
        Assertions.assertNotNull(exception);
    }

    @Test
    @Order(8)
    public void addStudentToCourseById() {
        Course testCourse = courseDao.findAllCourses().get(0);
        Student student = testCourse.getStudents().get(3);
        testCourse.getStudents().remove(3);
        courseDao.update(testCourse);
        setToSystemInputStreamValues(testCourse.getId() + "\n" + student.getId() + '\n');
        controller.addStudentToCourseById();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals(outputValues[2], "success adding");
        Course actualCourse = courseDao.findById(testCourse.getId());
        Assertions.assertNotEquals(testCourse, actualCourse);
        Assertions.assertEquals(actualCourse.getStudents().size(), testCourse.getStudents().size() + 1);
        for (int i = 0; i < testCourse.getStudents().size(); i++) {
            Assertions.assertEquals(actualCourse.getStudents().get(i), testCourse.getStudents().get(i));
        }
        Assertions.assertEquals(actualCourse.getStudents().get(actualCourse.getStudents().size() - 1), student);
    }

    @Test
    @Order(9)
    public void removeStudentFromCourseById() {
        Course testCourse = courseDao.findAllCourses().get(0);
        Student student = testCourse.getStudents().get(3);
        setToSystemInputStreamValues(testCourse.getId() + "\n" + student.getId() + '\n');
        controller.removeStudentFromCourseById();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals(outputValues[2], "success removing");
        Course actualCourse = courseDao.findById(testCourse.getId());
        Assertions.assertNotEquals(testCourse, actualCourse);
        Assertions.assertEquals(actualCourse.getStudents().size(), testCourse.getStudents().size() - 1);
        for (int i = 0; i < actualCourse.getStudents().size(); i++) {
            Assertions.assertNotEquals(actualCourse.getStudents().get(i).getId(), student.getId());
        }
    }
}