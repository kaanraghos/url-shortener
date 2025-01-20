package com.baser.url_shortener.controller.exception;

public class UrlExpiredException extends RuntimeException{
    public UrlExpiredException(String message) {
        super(message);
    }
}
