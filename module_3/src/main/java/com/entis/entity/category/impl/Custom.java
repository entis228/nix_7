package com.entis.entity.category.impl;

import com.entis.entity.category.Category;
import com.entis.entity.category.CategoryType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "custom_category")
public class Custom extends Category {

    public Custom() {
    }

    public Custom(String name, CategoryType type) {
        this.name = name;
        this.type = type;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setType(CategoryType type){
        this.type=type;
    }
}
