package com.ukim.finki.onlineshopseminarska.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class YouAlreadyHaveActiveShoppingCart extends RuntimeException{
    public YouAlreadyHaveActiveShoppingCart(String username) {
        super(String.format("Cart already created for user %s", username));

    }
}
