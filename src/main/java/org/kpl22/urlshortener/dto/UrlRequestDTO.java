package org.kpl22.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class UrlRequestDTO {

    @NotBlank
    private String url;

    /**
     * Time-to-live in seconds.
     * If null, URL does not expire.
     */
    @Positive
    private Long expiresInSeconds;

    public String getUrl() {
        return url;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }
}
