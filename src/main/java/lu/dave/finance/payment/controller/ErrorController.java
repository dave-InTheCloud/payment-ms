package lu.dave.finance.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.ForbiddenException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.exception.ServiceUnvailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errorResponse = new HashMap<>();

        // Get field errors and their corresponding messages
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            // Add errors to the map, grouping them by field name
            errorResponse.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @AllArgsConstructor
    @Data
    public class ErrorDto {
        private LocalDateTime timestamp;
        private int status;
        private List<String> error;
        private String path;

        public ErrorDto(int status, String message, HttpServletRequest httpServletRequest) {
            this.timestamp = LocalDateTime.now();
            this.status = status;
            this.path = httpServletRequest.getRequestURI();
            this.error = List.of(message);
        }
    }

    @ExceptionHandler({NotFoundException.class, ForbiddenException.class,
            ServiceUnvailableException.class, BadParameterException.class})
    public ResponseEntity<Object> handleCustomException(
            final RuntimeException exception, final HttpServletRequest request) {

        HttpStatus httpStatus = switch (exception) {
            // instance of
            case NotFoundException e -> HttpStatus.NOT_FOUND;
            case ForbiddenException e -> HttpStatus.FORBIDDEN;
            case BadParameterException e -> HttpStatus.BAD_REQUEST;
            case ServiceUnvailableException e -> HttpStatus.SERVICE_UNAVAILABLE;
            // ...
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        ErrorDto errorDto = new ErrorDto(httpStatus.value(), exception.getMessage(), request);
        return new ResponseEntity<>(errorDto, httpStatus);
    }
}
