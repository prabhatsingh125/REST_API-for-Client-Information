package com.prabhat.Client_Information.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends Exception{
    private Long client_id;

    public ClientNotFoundException(Long client_id) {
        super(String.format("Client is not found with this id : %s",client_id));
    }
}
