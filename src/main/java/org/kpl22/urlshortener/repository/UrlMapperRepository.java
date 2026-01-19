package org.kpl22.urlshortener.repository;

import org.kpl22.urlshortener.entity.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMapperRepository extends JpaRepository<UrlMapper, Integer> {

    Optional<UrlMapper> findUrlMapperByShortenedId(String shortenedId);
}
