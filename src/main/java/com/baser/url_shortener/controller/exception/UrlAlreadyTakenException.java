package com.baser.url_shortener.controller.exception;

public class UrlAlreadyTakenException extends RuntimeException{
    public UrlAlreadyTakenException(String message) {
        super(message);
    }
}
