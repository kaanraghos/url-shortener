package com.baser.url_shortener.repository;

import com.baser.url_shortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    boolean existsByLongUrl(String longUrl);
}
