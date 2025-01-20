package com.baser.url_shortener.controller.dto;

public class UrlShortenerRequest {

    private String longURL;
    private Integer ttlDay;

    public UrlShortenerRequest(String longURL, Integer ttlDay) {
        this.longURL = longURL;
        this.ttlDay = ttlDay;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public Integer getTtlDay() {
        return ttlDay;
    }

    public void setTtlDay(Integer ttlDay) {
        this.ttlDay = ttlDay;
    }

    @Override
    public String toString() {
        return "UrlShortenerRequest{" +
                "longURL='" + longURL + '\'' +
                ", ttlDay=" + ttlDay +
                '}';
    }
}
