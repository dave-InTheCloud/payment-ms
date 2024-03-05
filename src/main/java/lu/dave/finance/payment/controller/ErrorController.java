package lu.dave.finance.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lu.dave.finance.payment.dto.ErrorDto;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.ForbiddenException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.exception.ServiceUnvailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationErrors(MethodArgumentNotValidException ex, final HttpServletRequest request) {
        List<String> errorResponse = new ArrayList<>();

        // Get field errors and their corresponding messages
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            // Create ErrorEntry objects to store both field name and error message
            errorResponse.add(String.format("%s : %s", fieldName, errorMessage));
        });


        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorResponse, request);
        logErrorDto(" MethodArgumentNotValidException Occur for %{} with status {}: %{}", errorDto);

        return new ResponseEntity<>(errorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleMissingParameter(final MissingServletRequestParameterException ex,
                                                          final HttpServletRequest request ) {
        String missingParam = ex.getParameterName();
        String message = String.format("Missing required parameter: %s", missingParam);


        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), message, request);
        logErrorDto(" MissingServletRequestParameterException Occur for %{} with status {}: %{}", errorDto);

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class, ForbiddenException.class,
            ServiceUnvailableException.class, BadParameterException.class})
    public ResponseEntity<ErrorDto> handleCustomException(
            final RuntimeException exception, final HttpServletRequest request) {

     /* not working in java 17 in graalvm enable preview
       HttpStatus httpStatus = switch (exception) {
            // instance of
            case NotFoundException e -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };*/
        final HttpStatus httpStatus;
        if (exception instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (exception instanceof ForbiddenException) {
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (exception instanceof BadParameterException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof ServiceUnvailableException) {
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        ErrorDto errorDto = new ErrorDto(httpStatus.value(), exception.getMessage(), request);
        logErrorDto("A Runtime exception Occur for %{} with status {}: %{}", errorDto);
        return new ResponseEntity<>(errorDto, httpStatus);
    }

    private static void logErrorDto(String s, ErrorDto errorDto) {
        log.error(s, errorDto.getPath(), errorDto.getStatus(), errorDto.getError());
    }

}
