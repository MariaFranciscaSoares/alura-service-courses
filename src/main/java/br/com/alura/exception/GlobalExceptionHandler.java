package br.com.alura.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AluraException.class)
    public ResponseEntity<ErrorDetails> handleException(Exception e, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(),
                e.toString(),
                request.getDescription(false),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
    }
}
