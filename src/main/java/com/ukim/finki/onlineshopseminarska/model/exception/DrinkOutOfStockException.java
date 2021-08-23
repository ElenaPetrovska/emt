package com.ukim.finki.onlineshopseminarska.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class DrinkOutOfStockException extends RuntimeException{
    public DrinkOutOfStockException(String name) {
        super(String.format("Product %s is out od stock!", name));
    }
}
