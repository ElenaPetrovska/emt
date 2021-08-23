package com.ukim.finki.onlineshopseminarska.web;

import com.ukim.finki.onlineshopseminarska.model.Category;
import com.ukim.finki.onlineshopseminarska.model.exception.CategoryIsAlreadyInShoppingCart;
import com.ukim.finki.onlineshopseminarska.service.CategoryService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String getCategories(Model model){
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/add-new")
    public String addNewCategory(Model model){
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "add-category";
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public String saveCategory(@Valid Category category, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "add-category";
        }
        try{
            this.categoryService.save(category);
        }catch (Exception e){
            return "redirect:/categories?error=" + e.getMessage();
        }
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String editCategory(Model model, @PathVariable Long id){
        try{
            Category category = this.categoryService.findById(id);
            model.addAttribute("category", category);
            return "add-category";
        }catch (RuntimeException ex){
            return "redirect:/categories?error=" + ex.getMessage();
        }
    }

    @PostMapping("{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        try{
            this.categoryService.deleteById(id);
            return "redirect:/categories";
        }catch (CategoryIsAlreadyInShoppingCart ex){
            return String.format("redirect:/categories?error=" , ex.getMessage());
        }

    }

}
