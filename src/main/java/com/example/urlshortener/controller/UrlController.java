package com.example.urlshortener.controller;

import com.example.urlshortener.dto.AnalyticsResponse;
import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.UrlResponse;
import com.example.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final ShortUrlService service;

    public UrlController(ShortUrlService service) {
        this.service = service;
    }

    @GetMapping
    public List<UrlResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<UrlResponse> create(@Valid @RequestBody CreateUrlRequest request) {
        UrlResponse response = service.create(request);
        return ResponseEntity.created(URI.create("/api/urls/" + response.code())).body(response);
    }

    @GetMapping("/{code}")
    public UrlResponse find(@PathVariable String code) {
        return service.findByCode(code);
    }

    @GetMapping("/{code}/analytics")
    public AnalyticsResponse analytics(@PathVariable String code) {
        return service.analytics(code);
    }

    @DeleteMapping("/{code}")
    public Map<String, String> deactivate(@PathVariable String code) {
        service.deactivate(code);
        return Map.of("message", "Short URL deactivated", "code", code);
    }
}
