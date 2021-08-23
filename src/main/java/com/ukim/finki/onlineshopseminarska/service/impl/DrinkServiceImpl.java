package com.ukim.finki.onlineshopseminarska.service.impl;

import com.ukim.finki.onlineshopseminarska.model.Category;
import com.ukim.finki.onlineshopseminarska.model.Drink;
import com.ukim.finki.onlineshopseminarska.model.exception.DrinkIsAlreadyInShoppingCartException;
import com.ukim.finki.onlineshopseminarska.model.exception.DrinkNotFoundException;
import com.ukim.finki.onlineshopseminarska.repository.DrinkRepository;
import com.ukim.finki.onlineshopseminarska.service.CategoryService;
import com.ukim.finki.onlineshopseminarska.service.DrinkService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class DrinkServiceImpl implements DrinkService {

    private final DrinkRepository drinkRepository;
    private final CategoryService categoryService;

    public DrinkServiceImpl(DrinkRepository drinkRepository, CategoryService categoryService) {
        this.drinkRepository = drinkRepository;
        this.categoryService = categoryService;
    }


    @Override
    public List<Drink> findAll() {
        return this.drinkRepository.findAll();
    }

    @Override
    public List<Drink> findAllRedWines() {
        return this.drinkRepository.findAllByCategoryId((long) 8);
    }

    @Override
    public List<Drink> findAllWhiteWines() {
        return this.drinkRepository.findAllByCategoryId((long) 5);
    }

    @Override
    public List<Drink> findAllRoseWines() {
        return this.drinkRepository.findAllByCategoryId((long) 9);
    }

    @Override
    public List<Drink> findAllBeers() {
        return this.drinkRepository.findAllByCategoryId((long) 1);
    }

    @Override
    public List<Drink> findAllWhiskeys() {
        return this.drinkRepository.findAllByCategoryId((long) 6);
    }

    @Override
    public List<Drink> findAllWhiskeysWallpaper() {
        return this.drinkRepository.findAllByCategoryId((long) 10);
    }

    @Override
    public List<Drink> findAllByCategoryId(Long categoryId) {
        return drinkRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Drink> findAllSortedByQuantity(boolean asc) {
        if (asc) {
            return this.drinkRepository.findAllByQuantityAsc();
        }
        return this.drinkRepository.findAllByQuantityDesc();
    }

    @Override
    public Drink findById(Long id) {
        return this.drinkRepository.findById(id)
                .orElseThrow(()-> new DrinkNotFoundException(id));
    }

    @Override
    public Drink saveDrink(Drink drink, MultipartFile image) throws IOException {
        Category category = this.categoryService.findById(drink.getCategory().getId());
        drink.setCategory(category);
        if(image != null && !image.getName().isEmpty()){
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s",
                    image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            drink.setImageBase64(base64Image);
        }
        return this.drinkRepository.save(drink);
    }

    @Override
    public Drink updateDrink(Long id, Drink drink, MultipartFile image) throws IOException {
        Drink d = this.findById(id);
        Category c = this.categoryService.findById(drink.getCategory().getId());
        d.setCategory(c);
        d.setName(drink.getName());
        d.setQuantity(drink.getQuantity());
        if(image != null && !image.getName().isEmpty()){
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s,base64,%s",
                    image.getContentType(), Base64.getEncoder().encodeToString(bytes));
                    d.setImageBase64(base64Image);
        }
        return this.drinkRepository.save(drink);
    }

    @Override
    public void deleteById(Long id) {
        Drink drink = this.findById(id);
        if(drink.getShoppingCarts().size() > 0){
            throw new DrinkIsAlreadyInShoppingCartException(drink.getName());
        }
        this.drinkRepository.deleteById(id);
    }

    @Override
    public Long countAllByQuantityGreaterThan(Long quantity) {
        return this.drinkRepository.countAllByQuantityGreaterThan(quantity);
    }
}
