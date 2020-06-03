package br.com.lareira.service.exceptions;

public class BadRequestIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestIdException(String msg) {
        super(msg);
    }

    public BadRequestIdException(String msg, Throwable cause) {
        super(msg, cause);
    }

}