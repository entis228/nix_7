package com.entis.entity;

import com.entis.csvparser.anottations.CSVColumn;

import java.util.Date;

public class User{

    @CSVColumn("id")
    private long id;
    @CSVColumn("firstName")
    private String firstName;
    @CSVColumn("middleName")
    private String middleName;
    @CSVColumn("lastName")
    private String lastName;
    @CSVColumn("birthday")
    private long birthday;
    @CSVColumn("isMale")
    private boolean isMale;
    @CSVColumn("isActive")
    private boolean isActive;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", isMale=" + isMale +
                ", isActive=" + isActive +
                '}';
    }
    public User(){
        firstName="";
        middleName="";
        lastName="";
        birthday=new Date().getTime();
    }

    public User(long id, String firstName, String middleName, String lastName, Long birthday, boolean isMale, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.isMale = isMale;
        this.isActive = isActive;
    }
}
