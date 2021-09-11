package com.entis.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class Student implements Serializable {

    private String id;
    private String fullName;
    private Integer age;
    private String phoneNumber;

    @Override
    public String toString() {
        return fullName + "(id=" + id + ") age:" + age + " phone:" + phoneNumber;
    }
}
