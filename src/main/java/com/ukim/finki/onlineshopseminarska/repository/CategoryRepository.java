package com.ukim.finki.onlineshopseminarska.repository;

import com.ukim.finki.onlineshopseminarska.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    List<Category> findByName(String name);
    Category save(Category category);
    void deleteById(Long id);

}
