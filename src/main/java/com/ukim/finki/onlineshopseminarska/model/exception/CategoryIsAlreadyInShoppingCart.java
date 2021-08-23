package com.ukim.finki.onlineshopseminarska.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class CategoryIsAlreadyInShoppingCart extends RuntimeException{
    public CategoryIsAlreadyInShoppingCart(String categoryName) {
        super(String.format("Category %s is already in shopping cart ", categoryName));
    }
}
