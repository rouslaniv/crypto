package com.crypto.web.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RESTExceptionDTO {
    HttpStatus httpStatus;
    String errorMessage;
}
