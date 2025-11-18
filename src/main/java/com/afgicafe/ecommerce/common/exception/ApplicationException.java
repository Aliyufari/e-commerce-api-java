package com.afgicafe.ecommerce.common.exception;

import com.afgicafe.ecommerce.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}
