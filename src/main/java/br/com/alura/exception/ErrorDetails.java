package br.com.alura.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private final String message;
    private final String details;
    private final String endpoint;
    private final LocalDateTime timestamp;
}
