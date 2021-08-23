package com.ukim.finki.onlineshopseminarska.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.ukim.finki.onlineshopseminarska.enumarations.CartStatus;
import com.ukim.finki.onlineshopseminarska.model.Drink;
import com.ukim.finki.onlineshopseminarska.model.ShoppingCart;
import com.ukim.finki.onlineshopseminarska.model.User;
import com.ukim.finki.onlineshopseminarska.model.dto.ChargeRequest;
import com.ukim.finki.onlineshopseminarska.model.exception.*;
import com.ukim.finki.onlineshopseminarska.repository.ShoppingCartRepository;
import com.ukim.finki.onlineshopseminarska.service.DrinkService;
import com.ukim.finki.onlineshopseminarska.service.PaymentService;
import com.ukim.finki.onlineshopseminarska.service.ShoppingCartService;
import com.ukim.finki.onlineshopseminarska.service.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserService userService;
    private final DrinkService drinkService;
    private  final PaymentService paymentService;
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(UserService userService, DrinkService drinkService, PaymentService paymentService, ShoppingCartRepository shoppingCartRepository) {
        this.userService = userService;
        this.drinkService = drinkService;
        this.paymentService = paymentService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(()-> new ShoppingCartIsNotActiveException(userId));
    }

    @Override
    public List<ShoppingCart> findAllByUsername(String userId) {
        return this.shoppingCartRepository.findAllByUserUsername(userId);
    }

    @Override
    public ShoppingCart createNewShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(user.getUsername(),CartStatus.CREATED)){
            throw  new YouAlreadyHaveActiveShoppingCart(user.getUsername());
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addDrinkToShoppingCart(String userId, Long drinkId) {
        if(this.getActiveShoppingCart(userId) == null){
            ShoppingCart shoppingCart = this.createNewShoppingCart(userId);
            Drink drink = this.drinkService.findById(drinkId);
            for(Drink d: shoppingCart.getDrinks()){
                if(d.getId().equals(drinkId))
                    throw new DrinkIsAlreadyInShoppingCartException(d.getName());
            }
            shoppingCart.getDrinks().add(drink);
            return this.shoppingCartRepository.save(shoppingCart);
        }else{
            ShoppingCart shoppingCart1 = this.getActiveShoppingCart(userId);
            Drink drink = this.drinkService.findById(drinkId);
            shoppingCart1.getDrinks().add(drink);
            return this.shoppingCartRepository.save(shoppingCart1);
        }
    }

    @Override
    @Transactional
    public ShoppingCart removeDrinkFromShoppingCart(String userId, Long drinkId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.setDrinks(
                shoppingCart.getDrinks()
                            .stream()
                            .filter(drink -> !drink.getId().equals(drinkId))
                            .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String userId) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(userId);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
        shoppingCart.setStatus(CartStatus.CANCELED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) throws TransactionFailedException {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(()-> new ShoppingCartIsNotActiveException(userId));

        List<Drink> drinks = shoppingCart.getDrinks();
        float price = 0;
        for(Drink drink: drinks){
            if(drink.getQuantity() <= 0){
                throw new DrinkOutOfStockException(drink.getName());
            }
            drink.setQuantity(drink.getQuantity()-1);
            price += drink.getPrice();
        }

        Charge charge;
        try {
             charge = this.paymentService.pay(chargeRequest);
        }catch (StripeException ex){
            throw new TransactionFailedException(userId, ex.getMessage());
        }

        shoppingCart.setDrinks(drinks);
        shoppingCart.setStatus(CartStatus.FINISHED);
        shoppingCart.setEnd(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
