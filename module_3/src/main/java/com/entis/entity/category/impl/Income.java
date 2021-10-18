package com.entis.entity.category.impl;

import com.entis.entity.category.Category;
import com.entis.entity.category.CategoryType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "income_category")
public class Income extends Category {

    public Income() {
        this.type= CategoryType.INCREMENTING;
        this.name="Incomes";
    }
}
