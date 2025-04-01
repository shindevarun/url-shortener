// Data transfer Object to handle incoming requests for shortening an URL (helper class)
// Takes in JSON body as an input and called by the UrlShortenerController

package com.url_shortener.url_shortener.controller;

public class UrlRequest {
    private String longUrl;
    private String customShortCode;

    // Getters and setters
    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getCustomShortCode() {
        return customShortCode;
    }

    public void setCustomShortCode(String customShortCode) {
        this.customShortCode = customShortCode;
    }
}
