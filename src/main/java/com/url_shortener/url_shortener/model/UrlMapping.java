// Representation of a database entity for storing Url mappings to the table in PostgreSQL

package com.url_shortener.url_shortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="url_mapping")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-gen unique id for each mapping
    private Long id;

    @Column(nullable = false, unique = true) // make short code required and unique
    private String shortCode;

    @Column(nullable = false) // make long url required
    private String longUrl;

    private LocalDateTime createdAt; // metadata

    // base constructor
    public UrlMapping() {
    }

    // construct a new urlMapping
    public UrlMapping(String longUrl, String shortCode) {
        this.shortCode = shortCode;
        this.longUrl = longUrl;
        this.createdAt = LocalDateTime.now();
    }


    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
