package com.entis.entity;

import com.entis.anottations.PropertyKey;

import java.util.Date;

public class AppProperties {

    @PropertyKey("connection.id")
    private int id;

    @PropertyKey("connection.name")
    private String name;

    @PropertyKey("connection.time")
    private Date createdTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }
}
