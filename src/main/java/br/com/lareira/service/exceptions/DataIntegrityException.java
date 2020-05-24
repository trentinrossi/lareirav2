package br.com.lareira.service.exceptions;

// https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8169642#overview
public class DataIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataIntegrityException(String msg) {
        super(msg);
    }

    public DataIntegrityException(String msg, Throwable cause) {
        super(msg, cause);
    }

}