package com.example.desaqa.exception;

import org.springframework.http.HttpStatus;

public class ParamDateToInvalidException extends GenericException{
    public ParamDateToInvalidException(){
        super("La fecha de salida debe ser mayor a la de entrada", HttpStatus.BAD_REQUEST.value());
    }
}
