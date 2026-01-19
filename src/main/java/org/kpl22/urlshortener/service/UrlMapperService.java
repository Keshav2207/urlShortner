package org.kpl22.urlshortener.service;

import jakarta.transaction.Transactional;
import org.kpl22.urlshortener.dto.UrlRequestDTO;
import org.kpl22.urlshortener.entity.UrlMapper;
import org.kpl22.urlshortener.repository.UrlMapperRepository;
import org.kpl22.urlshortener.service.result.UrlMappingResult;
import org.kpl22.urlshortener.util.ShortIdGenerator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Service
public class UrlMapperService {

    private static final int SHORT_ID_LENGTH = 8;
    private static final int MAX_RETRIES = 5;

    private final UrlMapperRepository urlMapperRepository;

    public UrlMapperService(UrlMapperRepository urlMapperRepository) {
        this.urlMapperRepository = urlMapperRepository;
    }

    @Transactional
    public UrlMappingResult createUrlMapping(UrlRequestDTO dto) {

        // Idempotency: reuse active mapping
        Optional<UrlMapper> existing =
                urlMapperRepository.findFirstByOriginalUrlAndExpiresAtAfterOrExpiresAtIsNull(
                        dto.getUrl(),
                        Instant.now()
                );

        if (existing.isPresent()) {
            return new UrlMappingResult(existing.get(), false);
        }

        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            attempts++;

            String shortId = ShortIdGenerator.generate(SHORT_ID_LENGTH);

            try {
                UrlMapper entity = new UrlMapper(
                        dto.getUrl(),
                        shortId,
                        attempts,
                        Instant.now().plusSeconds(dto.getExpiresInSeconds())
                );

                return new UrlMappingResult(urlMapperRepository.save(entity), true);

            } catch (DataIntegrityViolationException ex) {
                // Collision on UNIQUE(shortened_id)
                // Retry with a new Base62 ID
            }
        }

        throw new IllegalStateException(
                "Unable to generate unique short ID after " + MAX_RETRIES + " attempts"
        );
    }

    public String getOriginalUrl(String shortId){
        return urlMapperRepository.findUrlMapperByShortenedId(shortId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Invalid short url."
        )).getOriginalUrl();
    }
}
