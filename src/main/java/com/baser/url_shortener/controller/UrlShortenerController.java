package com.baser.url_shortener.controller;

import com.baser.url_shortener.controller.dto.UrlShortenerRequest;
import com.baser.url_shortener.service.UrlShortenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@Slf4j
public class UrlShortenerController {

    public static final String SHORT_URL_DELETED_SUCCESSFULLY = "Short URL deleted successfully!";
    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createShortenUrl(@RequestBody UrlShortenerRequest urlShortenerRequest){
        log.info("Create url shorten request started with {}", urlShortenerRequest);
        String longUrl = urlShortenerService.shortenUrl(urlShortenerRequest);
        log.info("Create url shorten request finished with long url {}", longUrl);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(longUrl);
    }
    @GetMapping("/{shortenUrl}")
   public ResponseEntity<?> redirectUrl(@PathVariable String shortenUrl) throws URISyntaxException {
        log.info("Redirect request for short url came {}", shortenUrl);
        String longUrl = urlShortenerService.getLongUrlByShortUrl(shortenUrl);
        URI uri = new URI(longUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);

        log.info("Redirect request for completed. Redirection to  {}", longUrl);
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @DeleteMapping("/{shortenUrl}")
    public ResponseEntity<String> deleteUrl(@PathVariable String shortenUrl) {
        log.info("Delete request for short url came {}", shortenUrl);
        urlShortenerService.deleteShortUrl(shortenUrl);
        log.info("Delete request for short url completed {}", shortenUrl);
        return ResponseEntity.ok(SHORT_URL_DELETED_SUCCESSFULLY);
    }

}
