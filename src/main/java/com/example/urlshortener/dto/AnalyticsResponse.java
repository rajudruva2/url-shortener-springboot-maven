package com.example.urlshortener.dto;

public record AnalyticsResponse(
        String code,
        String originalUrl,
        long clicks,
        boolean active
) {}
