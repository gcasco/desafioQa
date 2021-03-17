package com.example.desaqa.exception;

import org.springframework.http.HttpStatus;

public class ParamOriginInvalidException extends GenericException{
    public ParamOriginInvalidException(){
        super("El origen elegido no existe", HttpStatus.BAD_REQUEST.value());
    }
}
