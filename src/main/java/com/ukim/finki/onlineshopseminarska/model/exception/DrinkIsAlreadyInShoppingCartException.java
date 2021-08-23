package com.ukim.finki.onlineshopseminarska.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class DrinkIsAlreadyInShoppingCartException extends RuntimeException {
    public DrinkIsAlreadyInShoppingCartException(String drinkName) {
        super(String.format("Drink %s is already in shopping cart ", drinkName));
    }
}
