package com.ukim.finki.onlineshopseminarska.service.impl;

import com.ukim.finki.onlineshopseminarska.model.Category;
import com.ukim.finki.onlineshopseminarska.model.exception.CategoryNotFoundException;
import com.ukim.finki.onlineshopseminarska.repository.CategoryRepository;
import com.ukim.finki.onlineshopseminarska.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category c = this.findById(id);
        c.setName(category.getName());
        c.setDescription(category.getDescription());
        return this.categoryRepository.save(c);
    }

    @Override
    public Category updateName(Long id, String name) {
        Category c = this.findById(id);
        c.setName(name);
        return this.categoryRepository.save(c);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
