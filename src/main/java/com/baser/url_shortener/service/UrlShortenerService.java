package com.baser.url_shortener.service;

import com.baser.url_shortener.controller.dto.UrlShortenerRequest;
import com.baser.url_shortener.controller.exception.UrlAlreadyTakenException;
import com.baser.url_shortener.controller.exception.UrlNotFoundException;
import com.baser.url_shortener.models.Url;
import com.baser.url_shortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.baser.url_shortener.service.HashUtils.generateHash;

@Service
@Slf4j
public class UrlShortenerService {

    public static final String DOMAIN="http://localhost:8080/";
    public static final String SHORT_URL_NOT_FOUND_SHOR_URL = "Short URL not found! ShorT url: %s";

    private final UrlRepository repository;

    @Autowired //constructor injection
    public UrlShortenerService(UrlRepository urlRepository){
        this.repository=urlRepository;
    }


    public String shortenUrl(UrlShortenerRequest request){

        String longUrl = request.getLongURL();

        if(repository.existsByLongUrl(longUrl)){
            throw new UrlAlreadyTakenException("This url has already shortened.");
        }

        String hash = generateHash(longUrl);
        String shortCode = hash.substring(0, 8);

        Url url = new Url(shortCode, longUrl, request.getTtlDay());

        repository.save(url);
        return DOMAIN + shortCode;
    }
    public void deleteShortUrl(String shortenUrl) {
        Optional<Url> optionalUrl = repository.findByShortUrl(shortenUrl);
        if (optionalUrl.isEmpty()) {
            log.info("Url to be deleted not found. {}", shortenUrl);
            throw new UrlNotFoundException(String.format(SHORT_URL_NOT_FOUND_SHOR_URL, shortenUrl));
        }

        repository.delete(optionalUrl.get());
    }

    public String getLongUrlByShortUrl(String shortenUrl) {
        Optional<Url> optionalUrl = repository.findByShortUrl(shortenUrl);

        if (optionalUrl.isEmpty()) {
            log.info("Url has not found. {}", shortenUrl);
            throw new UrlNotFoundException(String.format(SHORT_URL_NOT_FOUND_SHOR_URL, shortenUrl));
        }

        return optionalUrl.get().getLongUrl();
    }



}

