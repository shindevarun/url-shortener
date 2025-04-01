// main API controller that handles the API requests. uses 2 endpoints - /shorten (POST), /{shortCode} (GET)

package com.url_shortener.url_shortener.controller;

import com.url_shortener.url_shortener.model.UrlMapping;
import com.url_shortener.url_shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    // constructor creates instance of the service layer
    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    // accepts a JSON request body with a long URL and optional custom short code
    // generates / stores the short url using the service layer
    // return UrlMapping object with HTTP 201 code (created)
    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> shortenUrl(@RequestBody UrlRequest request) {
        UrlMapping urlMapping = urlShortenerService.shortenUrl(request.getLongUrl(), request.getCustomShortCode());
        return new ResponseEntity<>(urlMapping, HttpStatus.CREATED);
    }

    // accepts a short code
    // retrieves the long url using the service layer
    // If longUrl found -> returns HTTP 302 (Found) Redirect to the original URL
    // If longUrl !found -> returns HTTP 404 (Not Found) with error message
    @GetMapping("/{shortCode}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortCode) {
        Optional<UrlMapping> urlMapping = urlShortenerService.getOriginalUrl(shortCode);
        if (urlMapping.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND) // HTTP  Redirect
                    .header("Location", urlMapping.get().getLongUrl())
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found for the provided short code.");
        }
    }
}

