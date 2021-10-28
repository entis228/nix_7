package com.entis.service.impl;

import com.entis.entity.Lesson;
import com.entis.entity.Student;
import com.entis.service.LessonService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

public class NearestLessonService implements LessonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NearestLessonService.class);
    private static final Configuration configuration = new Configuration().configure();
    private final String dbUsername;
    private final String dbPassword;
    private SessionFactory currentFactory;
    private final EntityManager manager;

    public NearestLessonService(String dbUser, String dbPassword) {
        this.dbUsername = dbUser;
        this.dbPassword = dbPassword;
        openFactory();
        manager = currentFactory.createEntityManager();
    }

    private void openFactory() {
        configuration.setProperty("hibernate.connection.username", dbUsername);
        configuration.setProperty("hibernate.connection.password", dbPassword);
        currentFactory = configuration.buildSessionFactory();
    }

    @Override
    public List<Lesson> getLessons(Long idStudent) {
        LOGGER.info("Trying to get lessons");
        try {
            manager.getTransaction().begin();
            Student dbStudent = manager.find(Student.class, idStudent);
            List<Lesson> nearlyLessons = dbStudent.getGroup().getLessons().stream().filter(x -> x.getStartTime().isAfter(Instant.now())).min(Comparator.comparing(Lesson::getStartTime)).stream().toList();
            LOGGER.info("Success got lessons");
            manager.getTransaction().commit();
            return nearlyLessons;
        } catch (Exception e) {
            manager.getTransaction().rollback();
            LOGGER.error("Something wrong", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (currentFactory != null && !currentFactory.isClosed())
            currentFactory.close();
    }
}
