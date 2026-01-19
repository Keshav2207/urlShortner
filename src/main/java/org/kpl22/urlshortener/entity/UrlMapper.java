package org.kpl22.urlshortener.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@Entity
@Table(
        name = "url_mapper",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_shortened_id",
                        columnNames = "shortened_id"
                )
        },
        indexes = {
                @Index(
                        name = "idx_shortened_id",
                        columnList = "shortened_id"
                )
        }
)
public class UrlMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "original_url",
            nullable = false,
            length = 2048
    )
    private String originalUrl;

    /**
     * Choose a case-sensitive collation.
     * i.e. which does not end with *ci (case-insensitive)
     * example: choose column collation as 'utf8mb4_0900_bin'
     */
    @Column(
            name = "shortened_id",
            nullable = false,
            length = 16
    )
    private String shortenedId;

    @Comment("Number of attempts required to generate a unique short id")
    @Column(
            name = "generation_attempts",
            nullable = false
    )
    private Integer generationAttempts;

    @Comment("Timestamp after which this mapping is considered expired")
    @Column(
            name = "expires_at"
    )
    private Instant expiresAt;

    protected UrlMapper() {
        // JPA only
    }

    public UrlMapper(
            String originalUrl,
            String shortenedId,
            Integer generationAttempts,
            Instant expiresAt
    ) {
        this.originalUrl = originalUrl;
        this.shortenedId = shortenedId;
        this.generationAttempts = generationAttempts;
        this.expiresAt = expiresAt;
    }

    // Getters only

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenedId() {
        return shortenedId;
    }

    public Integer getGenerationAttempts() {
        return generationAttempts;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    // Derived state (no column)

    @Transient
    public boolean isActive() {
        return expiresAt == null || expiresAt.isAfter(Instant.now());
    }
}
