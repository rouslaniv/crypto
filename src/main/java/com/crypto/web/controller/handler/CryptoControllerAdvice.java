package com.crypto.web.controller.handler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crypto.web.dto.RESTExceptionDTO;
import com.crypto.web.exceptions.BadRequestStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ResponseBody
@RequestMapping
@ControllerAdvice
public class CryptoControllerAdvice {
	

    @ExceptionHandler({ BadRequestStatusException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RESTExceptionDTO handleBadRequestStatusException(HttpServletResponse response, Exception ex) {
        return processStatusException(response, ex, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public RESTExceptionDTO handleInternalErrorStatusException(HttpServletResponse response, Exception ex) {
        return processStatusException(response, ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(Exception ex, String errorMessage) {
        log.warn("Message: " + ex.getMessage() + ". Root exception: " + errorMessage, ex);
    }

    private RESTExceptionDTO processStatusException(HttpServletResponse response, Exception ex, HttpStatus httpStatus) {
        String errorMessage = ex.getMessage();
        logError(ex, errorMessage);
        return new RESTExceptionDTO(httpStatus, errorMessage);
    }

}
