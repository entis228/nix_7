package com.entis.service;

import com.entis.entity.Lesson;

import java.io.Closeable;
import java.util.List;

public interface LessonService extends Closeable {

    List<Lesson> getLessons(Long id);
}
