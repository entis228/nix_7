package com.entis.controller;

import com.entis.dao.InMemoryStudentDao;
import com.entis.data.Course;
import com.entis.data.Student;
import com.entis.service.CourseService;
import com.entis.util.StudentUtils;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentsControllerTest {

    //TODO с дао проще проверять+для дао отдельно написаны тесты, что вроде должно гарантировать надежность этого слоя
    //Сорри за дубляж кода, я устал
    private static final InMemoryStudentDao dao = new InMemoryStudentDao();
    private static final StudentController studentController = new StudentController();
    private static final ByteArrayOutputStream receiveOut = new ByteArrayOutputStream();
    private static InputStream sysIn;
    private static PrintStream sysOut;
    private static PrintStream customOutStream;
    private static List<Student> savedState;

    private static void setCustomOut() {
        try {
            customOutStream = new PrintStream(receiveOut, true, StandardCharsets.UTF_8.name());
            System.setOut(customOutStream);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    private static void init() {
        savedState = dao.findAllStudents();
        dao.clear();
        sysOut = System.out;
        sysIn = System.in;
        setCustomOut();
    }

    @AfterAll
    private static void resetStreams() {
        dao.clear();
        for (Student s : savedState) {
            dao.create(s);
        }
        customOutStream.close();
        System.setOut(sysOut);
        System.setIn(sysIn);
    }

    private void setStringToInput(String string) {
        System.setIn(new ByteArrayInputStream(string.getBytes()));
    }

    @Test
    @Order(2)
    public void findAll() {
        receiveOut.reset();
        List<Student> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student temp = StudentUtils.generateRandom();
            test.add(temp);
            dao.create(temp);
        }
        studentController.findAll();
        String[] outputValues = receiveOut.toString().split("\\n");
        String regex = ".+ .+ .+\\((id=).+\\) (age:)\\d+ (phone:)\\d+";
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < outputValues.length; i++) {
            boolean isMatch = pattern.matcher(outputValues[i]).matches();
            Assertions.assertTrue(isMatch);
            ArrayList<String> params = new ArrayList<>();
            String[] rawParams = outputValues[i].split("\\(id=");
            params.add(rawParams[0]);
            rawParams = rawParams[1].split("\\) ");
            params.add(rawParams[0]);
            rawParams = rawParams[1].split("[ :]");
            params.add(rawParams[1]);
            params.add(rawParams[3]);
            Assertions.assertEquals(params.get(0), test.get(i).getFullName());
            Assertions.assertEquals(params.get(1), test.get(i).getId());
            Assertions.assertEquals(params.get(2), test.get(i).getAge().toString());
            Assertions.assertEquals(params.get(3), test.get(i).getPhoneNumber());
        }
    }

    @Test
    @Order(3)
    public void createNormal() {
        dao.clear();
        receiveOut.reset();
        String inputValues = "Vasiliy Smith Stepanovich\n17\n0582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.create();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("the student was created", outputValues[3]);
        Student student = dao.findAllStudents().get(dao.findAllStudents().size() - 1);
        Assertions.assertEquals("Vasiliy Smith Stepanovich", student.getFullName());
        Assertions.assertEquals(17, student.getAge());
        Assertions.assertEquals("0582282281", student.getPhoneNumber());
    }

    @Test
    @Order(4)
    public void createAbnormalName() {
        dao.clear();
        receiveOut.reset();
        String inputValues = "Vasiliy Smith404\n17\n0582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.create();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("This student can't participate in the courses because fullname is not affordable", outputValues[3]);
    }

    @Test
    @Order(5)
    public void createAbnormalAge() {
        dao.clear();
        receiveOut.reset();
        String inputValues = "Vasiliy Smith Stepanovich\n10\n0582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.create();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("This student can't participate in the courses because age is not affordable", outputValues[3]);
        dao.clear();
        receiveOut.reset();
        inputValues = "Vasiliy Smith Stepanovich\n100\n0582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.create();
        outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("This student can't participate in the courses because age is not affordable", outputValues[3]);
    }

    @Test
    @Order(6)
    public void createAbnormalPhone() {
        dao.clear();
        receiveOut.reset();
        String inputValues = "Vasiliy Smith Stepanovich\n17\n05822822811\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.create();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("This student can't participate in the courses because phone number is not affordable", outputValues[3]);
        dao.clear();
        receiveOut.reset();
        inputValues = "Vasiliy Smith Stepanovich\n17\n058228f\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.create();
        outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("This student can't participate in the courses because phone number is not affordable", outputValues[3]);
    }

    @Test
    @Order(7)
    public void findById() {
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        Student student = dao.findAllStudents().get(0);
        receiveOut.reset();
        String inputValues = student.getId() + "\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.findById();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals(student.toString(), outputValues[1]);
    }

    @Test
    @Order(8)
    public void findByInvalidId() {
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        Student student = dao.findAllStudents().get(0);
        receiveOut.reset();
        String inputValues = "khwfkhfkjhsdfhd\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.findById();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("Not found", outputValues[1]);
    }

    @Test
    @Order(9)
    public void updateNormal() {
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        Student student = dao.findAllStudents().get(0);
        receiveOut.reset();
        String inputValues = student.getId() + "\nVasiliy Smith Stepanovich\n17\n0582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.update();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("The student was updated", outputValues[4]);
        Student actualStudent = dao.findById(student.getId());
        Assertions.assertNotEquals(student.toString(), actualStudent.toString());
        Assertions.assertEquals(1, dao.findAllStudents().size());
    }

    @Test
    @Order(9)
    public void updateAbnormalName() {
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        Student student = dao.findAllStudents().get(0);
        receiveOut.reset();
        String inputValues = student.getId() + "\n400Vasiliy Smith228\n17\n0582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.update();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("Invalid param fullname", outputValues[4]);
        Assertions.assertEquals(1, dao.findAllStudents().size());
        Assertions.assertEquals(student, dao.findById(student.getId()));
    }

    @Test
    @Order(11)
    public void updateAbnormalPhone() {
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        Student student = dao.findAllStudents().get(0);
        receiveOut.reset();
        String inputValues = student.getId() + "\nVasiliy Smith Stepanovich\n17\n380582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.update();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("Invalid param phone number", outputValues[4]);
        Assertions.assertEquals(1, dao.findAllStudents().size());
        Assertions.assertEquals(student, dao.findById(student.getId()));
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        student = dao.findAllStudents().get(0);
        receiveOut.reset();
        inputValues = student.getId() + "\nVasiliy Smith Stepanovich\n17\n+38O582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.update();
        outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("Invalid param phone number", outputValues[4]);
        Assertions.assertEquals(1, dao.findAllStudents().size());
        Assertions.assertEquals(student, dao.findById(student.getId()));
    }

    @Test
    @Order(10)
    public void updateAbnormalAge() {
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        Student student = dao.findAllStudents().get(0);
        receiveOut.reset();
        String inputValues = student.getId() + "\nVasiliy Smith Stepanovich\n6\n+380582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.update();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("Invalid param age", outputValues[4]);
        Assertions.assertEquals(1, dao.findAllStudents().size());
        Assertions.assertEquals(student, dao.findById(student.getId()));
        dao.clear();
        dao.create(StudentUtils.generateRandom());
        student = dao.findAllStudents().get(0);
        receiveOut.reset();
        inputValues = student.getId() + "\nVasiliy Smith Stepanovich\n90\n+380582282281\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.update();
        outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals("Invalid param age", outputValues[4]);
        Assertions.assertEquals(1, dao.findAllStudents().size());
        Assertions.assertEquals(student, dao.findById(student.getId()));
    }

    @Test
    @Order(12)
    public void delete() {
        dao.clear();
        for (int i = 0; i < 10; i++) {
            dao.create(StudentUtils.generateRandom());
        }
        Student testStudent = dao.findAllStudents().get(4);
        receiveOut.reset();
        String inputValues = testStudent.getId();
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        studentController.delete();
        String[] outputValues = receiveOut.toString().split("\\n");
        Assertions.assertEquals(outputValues[1], "removed");
        Assertions.assertEquals(9, dao.findAllStudents().size());
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> dao.findById(testStudent.getId()));
        Assertions.assertNotNull(exception);
        List<Course> courses = new CourseService().findAll();
        for (Course course : courses) {
            Assertions.assertFalse(course.getStudents().contains(testStudent));
        }
    }

    @Test
    @Order(13)
    public void deleteAbnormal() {
        dao.clear();
        for (int i = 0; i < 10; i++) {
            dao.create(StudentUtils.generateRandom());
        }
        receiveOut.reset();
        String inputValues = "random id\n";
        setStringToInput(inputValues);
        studentController.reader = new BufferedReader(new InputStreamReader(System.in));
        List<Student> beforeRemove = dao.findAllStudents();
        studentController.delete();
        Assertions.assertEquals(beforeRemove, dao.findAllStudents());
    }
}
