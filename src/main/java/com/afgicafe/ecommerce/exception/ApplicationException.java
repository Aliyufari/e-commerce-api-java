package com.afgicafe.ecommerce.exception;

import com.afgicafe.ecommerce.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<List<Map<String, String>>> handleValidation(MethodArgumentNotValidException exception) {
        List<Map<String, String>> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String, String> err = new HashMap<>();
            err.put(error.getField(), error.getDefaultMessage());
            errors.add(err);
        });

        return ApiResponse.error(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                "errors",
                errors
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<String> handleUnreadable(HttpMessageNotReadableException ex) {
        return ApiResponse.error(
                HttpStatus.BAD_REQUEST,
                "Invalid body",
                "error",
                ex.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ApiResponse.error(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                "error",
                ex.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResponse<String> handleArgumentMismatch(MethodArgumentTypeMismatchException ex) {

        String value = ex.getValue() != null ? ex.getValue().toString() : "unknown";

        return ApiResponse.error(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                "error",
                "No resource found with id " + value
        );
    }
}
