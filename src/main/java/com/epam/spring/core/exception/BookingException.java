package com.epam.spring.core.exception;

import com.epam.spring.core.service.BookingService;

/**
 * Created by Sergiy_Dakhniy
 */
public class BookingException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Booking exception: " + super.getMessage();
    }

    public BookingException(String message) {
        super(message);
    }
}
