package br.com.alura.exception;

public class AluraException extends RuntimeException {

    public AluraException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AluraException(String message) {
        super(message);
    }
}
