package com.example.desaqa.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatusCodeDTO {
    private Integer code;
    private String message;

    public StatusCodeDTO(Integer httpCode, String messsage){
        this.code=httpCode;
        this.message=messsage;
    }
}
