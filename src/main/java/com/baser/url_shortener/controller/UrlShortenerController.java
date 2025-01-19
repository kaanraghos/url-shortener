package com.baser.url_shortener.controller;

import com.baser.url_shortener.controller.dto.UrlShortenerRequest;
import com.baser.url_shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/create")
    public String createShortenUrl(@RequestBody UrlShortenerRequest urlShortenerRequest){
        return urlShortenerService.shortenUrl(urlShortenerRequest);
    }
    @GetMapping("/{shortenUrl}")
    public String redirectUrl (@PathVariable String shortenUrl){
        String longUrL = "Long url "+shortenUrl;
        return longUrL;
    }
    @DeleteMapping("/{shortenUrl}")
    public boolean deleteUrl(@PathVariable String shortenUrl){

        return false;
    }
}
