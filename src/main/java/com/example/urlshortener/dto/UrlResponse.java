package com.example.urlshortener.dto;

import com.example.urlshortener.entity.ShortUrl;

import java.time.LocalDateTime;

public record UrlResponse(
        Long id,
        String code,
        String originalUrl,
        String shortUrl,
        long clicks,
        LocalDateTime createdAt,
        boolean active
) {
    public static UrlResponse from(ShortUrl url) {
        return new UrlResponse(
                url.getId(),
                url.getCode(),
                url.getOriginalUrl(),
                "/r/" + url.getCode(),
                url.getClicks(),
                url.getCreatedAt(),
                url.isActive()
        );
    }
}
