package com.eyesecurity.cibersecurity.analytics_ingester.controller.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static Logger logger = LogManager.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Exception handleError(Exception error) {
        logger.error("An unhandled error occurred: {}", error.getMessage());
        return error;
    }


}
