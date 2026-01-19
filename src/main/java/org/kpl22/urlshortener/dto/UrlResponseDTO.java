package org.kpl22.urlshortener.dto;

public class UrlResponseDTO {
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public UrlResponseDTO(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
