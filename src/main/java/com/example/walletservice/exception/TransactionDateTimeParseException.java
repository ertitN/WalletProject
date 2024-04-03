package com.example.walletservice.exception;

public class TransactionDateTimeParseException extends RuntimeException {
    public TransactionDateTimeParseException(String message) {
        super(message);
    }
}
