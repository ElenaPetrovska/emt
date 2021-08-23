package com.ukim.finki.onlineshopseminarska.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3,message = "Name must be longer than 3 characters")
    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category"
//            ,orphanRemoval = true //This will remove all related entities
    )
    List<Drink> drinks;

    public Category() {
    }

    public Category(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
