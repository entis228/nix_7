package com.entis.entity.category;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Category {

    @Id
    private Long id;

    protected String name;

    @Enumerated(EnumType.ORDINAL)
    protected CategoryType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryType getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }
}
