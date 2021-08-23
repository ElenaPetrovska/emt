package com.ukim.finki.onlineshopseminarska.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class TransactionFailedException extends Throwable {
    public TransactionFailedException(String userId, String message) {
        super(String.format("Transaction from %s, failed %s",userId, message));
    }
}
