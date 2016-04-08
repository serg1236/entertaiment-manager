package com.epam.spring.core.exception;

/**
 * Created by Sergiy_Dakhniy
 */
public class FinanceException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Finance transaction failed: " + super.getMessage();
    }

    public FinanceException(String message) {
        super(message);
    }
}
