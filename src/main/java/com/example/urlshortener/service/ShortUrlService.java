package com.example.urlshortener.service;

import com.example.urlshortener.dto.AnalyticsResponse;
import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.UrlResponse;
import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.repository.ShortUrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@Service
public class ShortUrlService {

    private static final String ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final ShortUrlRepository repository;
    private final Random random = new Random();

    public ShortUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public UrlResponse create(CreateUrlRequest request) {
        String code;
        do {
            code = generateCode(7);
        } while (repository.existsByCode(code));

        return UrlResponse.from(repository.save(new ShortUrl(code, request.originalUrl())));
    }

    public List<UrlResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(UrlResponse::from)
                .toList();
    }

    public UrlResponse findByCode(String code) {
        ShortUrl url = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found"));
        return UrlResponse.from(url);
    }

    public String resolveAndCount(String code) {
        ShortUrl url = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found"));

        if (!url.isActive()) {
            throw new ResponseStatusException(HttpStatus.GONE, "Short URL is inactive");
        }

        url.incrementClicks();
        repository.save(url);
        return url.getOriginalUrl();
    }

    public AnalyticsResponse analytics(String code) {
        ShortUrl url = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found"));

        return new AnalyticsResponse(
                url.getCode(),
                url.getOriginalUrl(),
                url.getClicks(),
                url.isActive()
        );
    }

    public void deactivate(String code) {
        ShortUrl url = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found"));
        url.setActive(false);
        repository.save(url);
    }

    private String generateCode(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }
}
