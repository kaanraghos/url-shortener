package com.baser.url_shortener.repository;

import com.baser.url_shortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    boolean existsByLongUrl(String longUrl);

    Optional<Url> findByShortUrl(String shortenUrl);
}
