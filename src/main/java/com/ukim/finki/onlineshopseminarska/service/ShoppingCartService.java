package com.ukim.finki.onlineshopseminarska.service;

import com.ukim.finki.onlineshopseminarska.model.ShoppingCart;
import com.ukim.finki.onlineshopseminarska.model.dto.ChargeRequest;
import com.ukim.finki.onlineshopseminarska.model.exception.TransactionFailedException;

import java.util.*;

public interface ShoppingCartService {

    ShoppingCart findActiveShoppingCartByUsername(String userId);
    List<ShoppingCart> findAllByUsername(String userId);
    ShoppingCart createNewShoppingCart(String userId);
    ShoppingCart addDrinkToShoppingCart(String userId, Long drinkId);
    ShoppingCart removeDrinkFromShoppingCart(String userId, Long drinkId);
    ShoppingCart getActiveShoppingCart(String userId);
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) throws TransactionFailedException;
}
