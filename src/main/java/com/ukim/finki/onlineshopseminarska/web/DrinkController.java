package com.ukim.finki.onlineshopseminarska.web;

import com.ukim.finki.onlineshopseminarska.model.Category;
import com.ukim.finki.onlineshopseminarska.model.Drink;
import com.ukim.finki.onlineshopseminarska.model.exception.DrinkIsAlreadyInShoppingCartException;
import com.ukim.finki.onlineshopseminarska.service.CategoryService;
import com.ukim.finki.onlineshopseminarska.service.DrinkService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/drinks")
public class DrinkController {

    private DrinkService drinkService;
    private CategoryService categoryService;

    public DrinkController(DrinkService drinkService, CategoryService categoryService) {
        this.drinkService = drinkService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getDrinkPage(Model model){
//        List<Drink> drinks = this.drinkService.findAllWhiskeys();
//        model.addAttribute("drinks", drinks);
        return "index";
    }

    @GetMapping("/red")
    public String getRedWinePage(Model model){
        List<Drink> drinks = this.drinkService.findAllRedWines();
        model.addAttribute("drinks", drinks);
        return "redWine";
    }

    @GetMapping("/white")
    public String getWhiteWinePage(Model model){
        List<Drink> drinks = this.drinkService.findAllWhiteWines();
        model.addAttribute("drinks", drinks);
        return "whiteWine";
    }

    @GetMapping("/rose")
    public String getRoseWinePage(Model model){
        List<Drink> drinks = this.drinkService.findAllRoseWines();
        model.addAttribute("drinks", drinks);
        return "roseWine";
    }


    @GetMapping("/add-new")
    public String addNewDrinkPage(Model model){
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("drink", new Drink());
        return "add-drink";
    }

    @GetMapping("{id}/edit")
    public String editDrinkPage(Model model, @PathVariable Long id){
        try{
            Drink drink = this.drinkService.findById(id);
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("drink", drink);
            model.addAttribute("categories", categories);
            return "add-drink";
        }catch (RuntimeException ex){
            return "redirect:/drinks?error=" + ex.getMessage();
        }
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public String SaveDrink(@Valid Drink drink, BindingResult bindingResult, @RequestParam MultipartFile image,Model model){
        if(bindingResult.hasErrors()){
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            return "add-drink";
        }
        try {
            this.drinkService.saveDrink(drink, image);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/drinks";
    }

    @PostMapping("{id}/delete")
    public String deleteDrink(@PathVariable Long id){
        try{
            this.drinkService.deleteById(id);
            return "redirect:/index";
        }catch (DrinkIsAlreadyInShoppingCartException ex){
            return "redirect:/drinks?error=" + ex.getMessage();
        }
    }

}
