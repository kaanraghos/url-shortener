package com.baser.url_shortener.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "urls")
@EntityListeners(AuditingEntityListener.class)
public class Url {
    @Id
    private String id;
    @Column(nullable = false)
    private String shortUrl;
    @Column(nullable = false)
    private String longUrl;
    private Integer ttlDay;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Url() {
        this.id = UUID.randomUUID().toString();
    }

    public Url(String shortUrl, String longUrl, Integer ttlDay) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.ttlDay = ttlDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Integer getTtlDay() {
        return ttlDay;
    }

    public void setTtlDay(Integer ttlDay) {
        this.ttlDay = ttlDay;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
