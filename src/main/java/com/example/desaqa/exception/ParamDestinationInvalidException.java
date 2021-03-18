package com.example.desaqa.exception;

import org.springframework.http.HttpStatus;

public class ParamDestinationInvalidException extends GenericException{
    public ParamDestinationInvalidException(){
        super("El destino elegido no existe", HttpStatus.BAD_REQUEST.value());
    }
}
