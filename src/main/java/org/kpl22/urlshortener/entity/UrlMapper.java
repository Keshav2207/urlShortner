package org.kpl22.urlshortener.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
public class UrlMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String originalUrl;

    @Column(name = "url_mapped_shortened_id")
    private String shortenedId;

    @Comment("Denotes the number of attempts required to generate a unique short id")
    @Column(name = "short_id_generation_attempts")
    private Integer retries = 1;

    public UrlMapper() {
    }

    public UrlMapper(String originalUrl, String shortenedId, Integer retries) {
        this.originalUrl = originalUrl;
        this.shortenedId = shortenedId;
        this.retries = retries;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortenedId() {
        return shortenedId;
    }

    public void setShortenedId(String shortenedId) {
        this.shortenedId = shortenedId;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }
}
