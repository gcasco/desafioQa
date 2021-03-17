package com.example.desaqa.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenericException extends Exception{
    private String message;
    private Integer statusCode;
    public GenericException(String message, Integer status){
        this.message=message;
        this.statusCode=status;
    }
}
