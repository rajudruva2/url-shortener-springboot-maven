package com.example.urlshortener.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String code;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false)
    private long clicks;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean active = true;

    public ShortUrl() {}

    public ShortUrl(String code, String originalUrl) {
        this.code = code;
        this.originalUrl = originalUrl;
        this.createdAt = LocalDateTime.now();
        this.active = true;
        this.clicks = 0;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getOriginalUrl() { return originalUrl; }
    public long getClicks() { return clicks; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }
    public void incrementClicks() { this.clicks++; }
}
