package com.prabhat.Client_Information.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidMobileNumberException extends Exception{
    public static final long serialVersionUID = 1L;

    public InvalidMobileNumberException(String message) {
        super(message);
    }
}
