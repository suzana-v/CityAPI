package com.svulinovic.cityapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadRequestException.class, UnauthorizedRequestException.class})
    public ResponseEntity<ExceptionInfo> handleValidationException(RuntimeException e) {
        e.printStackTrace();
        ExceptionInfo info = new ExceptionInfo(e.getMessage());
        HttpStatus responseStatus = resolveAnnotatedResponseStatus(e);
        return new ResponseEntity<>(info, responseStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInfo> handleException(Exception e) {
        e.printStackTrace();
        ExceptionInfo info = new ExceptionInfo(ExceptionConstants.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(info, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
