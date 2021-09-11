package com.entis.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Course implements Serializable {

    private String id;
    private String name;
    private List<Student> students;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Course name: " + name + "(id=" + id + ")" + "\nStudents:\n");
        for (Student s : students) {
            builder.append(s).append("\n");
        }
        return builder.toString();
    }
}