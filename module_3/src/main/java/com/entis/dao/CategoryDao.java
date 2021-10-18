package com.entis.dao;

import com.entis.entity.category.Category;

public interface CategoryDao {

    void create(Category category);

    Category findById(Long id);

    void update(Category category);

    void deleteById(Long id);
}
