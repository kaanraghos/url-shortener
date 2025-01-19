package com.baser.url_shortener.service;

import com.baser.url_shortener.controller.dto.UrlShortenerRequest;
import com.baser.url_shortener.controller.exception.UrlAlreadyTakenException;
import com.baser.url_shortener.models.Url;
import com.baser.url_shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.baser.url_shortener.service.HashUtils.generateHash;

@Service
public class UrlShortenerService {

    public static final String DOMAIN="http://localhost:8080/";

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

}

