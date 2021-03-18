package com.example.desaqa.exception;

import org.springframework.http.HttpStatus;

public class ParamInputInvalidException extends GenericException{
    public ParamInputInvalidException(String msg){
        super(msg, HttpStatus.BAD_REQUEST.value());
    }
}
