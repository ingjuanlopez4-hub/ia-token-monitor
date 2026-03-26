package com.tuorganizacion.tokenmonitor.domain.exception;

public class TokenParsingException extends RuntimeException {
    public TokenParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
