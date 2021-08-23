package com.ukim.finki.onlineshopseminarska.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "Drinks")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Float price;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 0, message="Number of samples must be greater than 0")
    private Long quantity;

    @Column(name = "image")
    @Lob
    private String imageBase64;

    @NotNull
    @ManyToOne
    private Category category;

    @JsonIgnore
    @ManyToMany
    private List<ShoppingCart> shoppingCarts;


}
