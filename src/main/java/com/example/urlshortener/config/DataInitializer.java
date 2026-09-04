package com.example.urlshortener.config;

import com.example.urlshortener.entity.ShortUrl;
import com.example.urlshortener.repository.ShortUrlRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(ShortUrlRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                ShortUrl a = new ShortUrl("spring01", "https://spring.io/");
                a.incrementClicks();
                a.incrementClicks();

                ShortUrl b = new ShortUrl("github01", "https://github.com/");
                b.incrementClicks();

                repository.save(a);
                repository.save(b);
            }
        };
    }
}
