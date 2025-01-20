package com.baser.url_shortener.service;

import com.baser.url_shortener.controller.dto.UrlShortenerRequest;
import com.baser.url_shortener.controller.exception.UrlAlreadyTakenException;
import com.baser.url_shortener.controller.exception.UrlExpiredException;
import com.baser.url_shortener.controller.exception.UrlNotFoundException;
import com.baser.url_shortener.models.Url;
import com.baser.url_shortener.repository.UrlRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UrlShortenerServiceTest {

    public static final String TEST_LONG_URL_COM = "test.long.url.com";
    public static final int TTL_DAY = 7;
    public static final String SHORT_URL = "TST_SHRT";
    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shortenUrl_throwsUrlAlreadyTakenException() {
        urlRepository.existsByLongUrl(TEST_LONG_URL_COM);
        Mockito.when(urlRepository.existsByLongUrl(TEST_LONG_URL_COM))
                .thenReturn(true);

        assertThrows(UrlAlreadyTakenException.class, () ->
                urlShortenerService.shortenUrl(new UrlShortenerRequest(TEST_LONG_URL_COM, TTL_DAY)));
    }

    @Test
    void shortenUrl_returnsLongUrl() {
        Mockito.when(urlRepository.existsByLongUrl(TEST_LONG_URL_COM))
                .thenReturn(false);

        String response = urlShortenerService.shortenUrl(new UrlShortenerRequest(TEST_LONG_URL_COM, TTL_DAY));
        assertTrue(response.contains(UrlShortenerService.DOMAIN));

    }

    @Test
    void deleteShortUrl_throwsUrlNotFoundException() {
        Mockito.when(urlRepository.findByShortUrl(SHORT_URL))
                .thenReturn(Optional.empty());
        assertThrows(UrlNotFoundException.class, () ->
                urlShortenerService.deleteShortUrl(SHORT_URL));

    }
    @Test
    void deleteShortUrl_succeed() {
        Url url = new Url(SHORT_URL, TEST_LONG_URL_COM, System.currentTimeMillis() + 60 * 24 * 1000 * 7);
        Mockito.when(urlRepository.findByShortUrl(SHORT_URL))
                .thenReturn(Optional.of(url));
        urlShortenerService.deleteShortUrl(SHORT_URL);

    }

    @Test
    void getLongUrlByShortUrl_throwsUrlNotFoundException() {
        Mockito.when(urlRepository.findByShortUrl(SHORT_URL))
                .thenReturn(Optional.empty());
        assertThrows(UrlNotFoundException.class, () ->
                urlShortenerService.getLongUrlByShortUrl(SHORT_URL));

    }
    @Test
    void getLongUrlByShortUrl_throwsUrlExpiredException() {
        Url url = new Url(SHORT_URL, TEST_LONG_URL_COM, System.currentTimeMillis() - 60 * 24 * 1000 * 7);
        Mockito.when(urlRepository.findByShortUrl(SHORT_URL))
                .thenReturn(Optional.of(url));
        assertThrows(UrlExpiredException.class, () ->
                urlShortenerService.getLongUrlByShortUrl(SHORT_URL));

    }
    @Test
    void getLongUrlByShortUrl_succeed() {
        Url url = new Url(SHORT_URL, TEST_LONG_URL_COM, System.currentTimeMillis() + 60 * 24 * 1000 * 7);
        Mockito.when(urlRepository.findByShortUrl(SHORT_URL))
                .thenReturn(Optional.of(url));
        String response = urlShortenerService.getLongUrlByShortUrl(SHORT_URL);
        assertEquals(TEST_LONG_URL_COM, response);

    }
}