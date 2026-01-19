package org.kpl22.urlshortener.repository;

import org.kpl22.urlshortener.entity.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface UrlMapperRepository extends JpaRepository<UrlMapper, Long> {

    Optional<UrlMapper> findFirstByOriginalUrlAndExpiresAtAfterOrExpiresAtIsNull(
            String originalUrl,
            Instant now
    );

    Optional<UrlMapper> findUrlMapperByShortenedId(String shortenedId);
}
