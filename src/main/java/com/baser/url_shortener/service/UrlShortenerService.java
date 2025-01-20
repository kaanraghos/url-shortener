package com.baser.url_shortener.service;

import com.baser.url_shortener.controller.dto.UrlShortenerRequest;
import com.baser.url_shortener.controller.exception.UrlAlreadyTakenException;
import com.baser.url_shortener.controller.exception.UrlExpiredException;
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
    public static final String SHORT_URL_NOT_FOUND_SHORT_URL = "Short URL not found! ShorT url: %s";
    public static final String SHORT_URL_EXPIRED = "Short URL has expired! ShorT url: %s";
    public static final int DAY_TO_MILIS = 24 * 60 * 60 * 1000;

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

        Url url = new Url(shortCode, longUrl, System.currentTimeMillis()+ (request.getTtlDay() * DAY_TO_MILIS));

        repository.save(url);
        return DOMAIN + shortCode;
    }
    public void deleteShortUrl(String shortenUrl) {
        Optional<Url> optionalUrl = repository.findByShortUrl(shortenUrl);
        if (optionalUrl.isEmpty()) {
            log.error("Url to be deleted not found. {}", shortenUrl);
            throw new UrlNotFoundException(String.format(SHORT_URL_NOT_FOUND_SHORT_URL, shortenUrl));
        }

        Url url = optionalUrl.get();
        url.setExpirationTime(System.currentTimeMillis());
        log.info("Url is now expired. {}", shortenUrl);

        repository.save(url);
    }

    public String getLongUrlByShortUrl(String shortenUrl) {
        Optional<Url> optionalUrl = repository.findByShortUrl(shortenUrl);

        if (optionalUrl.isEmpty()) {
            log.info("Url has not found. {}", shortenUrl);
            throw new UrlNotFoundException(String.format(SHORT_URL_NOT_FOUND_SHORT_URL, shortenUrl));
        }

        Url url = optionalUrl.get();
        if (url.getExpirationTime()<System.currentTimeMillis()) {
            log.info("Url requested has expired. {}", shortenUrl);
            throw new UrlExpiredException(String.format(SHORT_URL_EXPIRED, shortenUrl));
        }
        return url.getLongUrl();
    }



}

