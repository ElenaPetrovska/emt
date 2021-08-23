package com.ukim.finki.onlineshopseminarska.service;

import com.ukim.finki.onlineshopseminarska.model.Category;
import java.util.*;

public interface CategoryService {
    List<Category> findAll();
    List<Category> findAllByName(String name);
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    Category updateName(Long id, String name);
    void deleteById(Long id);
}
