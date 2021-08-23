package com.ukim.finki.onlineshopseminarska.service;

import com.ukim.finki.onlineshopseminarska.model.Drink;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface DrinkService {
    List<Drink> findAll();
    List<Drink> findAllRedWines();
    List<Drink> findAllWhiteWines();
    List<Drink> findAllRoseWines();
    List<Drink> findAllBeers();
    List<Drink> findAllWhiskeys();
    List<Drink> findAllWhiskeysWallpaper();
    List<Drink> findAllByCategoryId(Long categoryId);
    List<Drink> findAllSortedByQuantity(boolean asc);
    Drink findById(Long id);
    Drink saveDrink(Drink drink, MultipartFile image)throws IOException;
    Drink updateDrink(Long id, Drink drink, MultipartFile image)throws IOException;
    void deleteById(Long id);
    Long countAllByQuantityGreaterThan(@Param("quantity") Long quantity);

}
