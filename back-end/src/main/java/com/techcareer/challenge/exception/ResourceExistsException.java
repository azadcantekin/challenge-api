package com.techcareer.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceExistsException extends RuntimeException{

    public ResourceExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
