package com.afgicafe.ecommerce.exception;

import com.afgicafe.ecommerce.helper.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class ApplicationException {

    private final ObjectMapper objectMapper;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, List<String>>> handleValidation(MethodArgumentNotValidException exception) {

        Map<String, List<String>> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            PropertyNamingStrategies.SnakeCaseStrategy strategy =
                    new PropertyNamingStrategies.SnakeCaseStrategy();

            String field = strategy.translate(error.getField());


            String message = error.getDefaultMessage();

            errors.computeIfAbsent(field, key -> new ArrayList<>())
                    .add(message);
        });

        return ApiResponse.error(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                "errors",
                errors
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage()));
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
