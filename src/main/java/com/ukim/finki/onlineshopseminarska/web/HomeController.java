package com.ukim.finki.onlineshopseminarska.web;

import com.ukim.finki.onlineshopseminarska.model.Drink;
import com.ukim.finki.onlineshopseminarska.service.DrinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    private final DrinkService drinkService;

    public HomeController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping
    public String indexPage(Model model){
        List<Drink> drinks = this.drinkService.findAllWhiskeysWallpaper();
        model.addAttribute("drinks", drinks);
        return "index";
    }

    @GetMapping("/index")
    public String getDrinkPage(Model model){
        List<Drink> drinks = this.drinkService.findAllWhiskeysWallpaper();
        model.addAttribute("drinks", drinks);
        return "index";
    }
//
//    @GetMapping("/home")
//    public String getHomePage(HttpServletResponse res, HttpServletRequest req){
//        return "home";
//    }
}
