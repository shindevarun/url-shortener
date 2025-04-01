// service layer that is responsible for the business logic
// main purpose is to store and retrieve URL mappings from the db using the UrlMappingRepository

package com.url_shortener.url_shortener.service;

import com.url_shortener.url_shortener.model.UrlMapping;
import com.url_shortener.url_shortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UrlShortenerService {
    private final UrlMappingRepository urlMappingRepository;
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // constructor. using @autowired for dependency injection (no need to create new instance of urlrepo)
    @Autowired
    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }


    // create the short url given a long url and possible custom shortcode and saves to database
    // returns a copy of the UrlMapping object that was saved to db
    // throws exception if custom short code is already in use
    public UrlMapping shortenUrl(String longUrl, String customShortCode) {

        String shortCode = "";
        if (customShortCode == null || customShortCode.trim().isEmpty()) {
            shortCode = shortCode + generateRandomShortCode(longUrl);
        } else {
            if(urlMappingRepository.findByShortCode(customShortCode) != null) {
                throw new IllegalArgumentException("Custom short code already in use. Please choose a different one.");
            } else {
                shortCode = shortCode + customShortCode;
            }
        }

        UrlMapping urlMapping = new UrlMapping(longUrl, shortCode);
        return urlMappingRepository.save(urlMapping);
    }


    // retrieves the original url from the db given a shortcode
    // if shortcode exists, returns Optional.of(UrlMapping), else Optional.empty()
    public Optional<UrlMapping> getOriginalUrl(String shortCode) {
        return Optional.ofNullable(urlMappingRepository.findByShortCode(shortCode));
    }


    // helper function to generate unique 6-character short code
    // original url is passed through a sha-256 hashing function to generate unique hashes
    // throws a runtime exception if sha-256 is unavaiable
    private String generateRandomShortCode(String longUrl) {
        // Generate a random alphanumeric string
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(longUrl.getBytes(StandardCharsets.UTF_8));

            // convert hash (hex) to base62
            String shortCode = encodeBase62(hash).substring(0, 6); // save first 6 chars as unique shortcode

            // ensure uniqueness incase user is trying to hash a longUrl that has already been hashed
            int attempt = 1;
            while(urlMappingRepository.findByShortCode(shortCode) != null) {
                shortCode = encodeBase62(digest.digest((longUrl + attempt).getBytes(StandardCharsets.UTF_8))).substring(0, 6);
                attempt++;
            }

            return shortCode;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }


    // encode to base62 so that collisions are unlikely. for 6-digit code, base62 can represent 62^6 unique values
    private String encodeBase62(byte[] hash) {
        StringBuilder sb = new StringBuilder();

        for(byte b : hash) {
            sb.append(BASE62.charAt((b & 0xFF) % 62)); // java has byte vals range from -128 to 127. b & 0xFF converts this to a val between 0 and 255
        }

        return sb.toString();
    }
}
