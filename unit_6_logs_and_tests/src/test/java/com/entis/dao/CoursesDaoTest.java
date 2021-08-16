package com.entis.dao;

import com.entis.data.Course;
import com.entis.util.CourseUtils;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoursesDaoTest {

    private static final InMemoryCourseDao courseDao = new InMemoryCourseDao();
    private static List<Course> beforeState = null;
    private static boolean isCouldBeSaved = false;

    private static void saveState() {
        if (isCouldBeSaved)
            beforeState = courseDao.findAllCourses();
    }

    @AfterAll
    private static void returnState() {
        if (isCouldBeSaved) {
            courseDao.clear();
            for (Course s : beforeState) {
                courseDao.create(s);
            }
        }
    }

    private boolean isUniqueIds(List<Course> lst) {
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
        List<Course> courses = courseDao.findAllCourses();
        Assertions.assertNotEquals(null, courses);
        isCouldBeSaved = true;
    }

    @Test
    @Order(2)
    public void createAndClear() {
        saveState();
        for (int i = 0; i < 10; i++)
            courseDao.create(CourseUtils.generateRandom());
        List<Course> testList = courseDao.findAllCourses();
        courseDao.create(CourseUtils.generateRandom());
        List<Course> listWithCourse = courseDao.findAllCourses();
        Assertions.assertEquals(testList.size() + 1, listWithCourse.size());
        Assertions.assertTrue(isUniqueIds(listWithCourse));
        courseDao.clear();
        for (int i = 0; i < 10; i++)
            courseDao.create(CourseUtils.generateRandom());
        courseDao.clear();
        List<Course> empty = courseDao.findAllCourses();
        Assertions.assertEquals(0, empty.size());
    }

    @Test
    @Order(3)
    public void getById() {
        courseDao.clear();
        courseDao.create(CourseUtils.generateSimple());
        for (int i = 0; i < 10; i++)
            courseDao.create(CourseUtils.generateRandom());
        List<Course> testState = courseDao.findAllCourses();
        Course inList = testState.get(0);
        Course expected = courseDao.findById(inList.getId());
        List<Course> newState = courseDao.findAllCourses();
        Assertions.assertEquals(testState.size(), newState.size());
        Assertions.assertEquals(inList.getId(), expected.getId());
        Assertions.assertEquals(inList, expected);
        Assertions.assertTrue(isUniqueIds(newState));
    }

    @Test
    @Order(4)
    public void update() {
        List<Course> lastState = courseDao.findAllCourses();
        Course oldCourse = lastState.get(0);
        Course newCourse = CourseUtils.generateRandom();
        newCourse.setId(oldCourse.getId());
        courseDao.update(newCourse);
        oldCourse = courseDao.findById(newCourse.getId());
        List<Course> newState = courseDao.findAllCourses();
        Assertions.assertEquals(lastState.size(), newState.size());
        Assertions.assertEquals(newCourse.getId(), oldCourse.getId());
        Assertions.assertEquals(newCourse, oldCourse);
        Assertions.assertTrue(isUniqueIds(newState));
    }

    @Test
    @Order(5)
    public void delete() {
        courseDao.create(CourseUtils.generateRandom());
        List<Course> dbList = courseDao.findAllCourses();
        Course course = dbList.get(dbList.size() - 1);
        courseDao.delete(course.getId());
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> courseDao.findById(course.getId()));
        Assertions.assertNotNull(exception);
        List<Course> newState = courseDao.findAllCourses();
        Assertions.assertEquals(dbList.size() - 1, newState.size());
        for (Course s : newState) {
            Assertions.assertNotEquals(s.getId(), course.getId());
        }
        Assertions.assertTrue(isUniqueIds(newState));
    }
}
