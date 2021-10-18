package com.entis.entity.category.impl;

import com.entis.entity.category.Category;
import com.entis.entity.category.CategoryType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expense_category")
public class Expense extends Category {

    public Expense() {
        this.type=CategoryType.DECREMENTING;
        this.name="Expenses";
    }
}
