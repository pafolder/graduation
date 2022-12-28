package com.pafolder.graduation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> processException(ResponseStatusException ex, WebRequest request) throws Exception {
        log.error("Exception: {}", ex.getMessage());
//        return handleExceptionInternal(ex, ex.getReason(), new HttpHeaders(), httpStatus, request);
        return handleExceptionInternal(ex, createProblemDetail(ex, ex.getStatusCode(), ex.getReason(), null, null, request),
                new HttpHeaders(), ex.getStatusCode(), request);
    }
}