package org.kpl22.urlshortener.service;

import org.kpl22.urlshortener.dto.UrlRequestDTO;
import org.kpl22.urlshortener.entity.UrlMapper;
import org.kpl22.urlshortener.repository.UrlMapperRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlMapperService {

    private final UrlMapperRepository urlMapperRepository;

    public UrlMapperService(UrlMapperRepository urlMapperRepository) {
        this.urlMapperRepository = urlMapperRepository;
    }

    public UrlMapper createUrlMapping(UrlRequestDTO dto){
        String uuid = UUID.randomUUID().toString().substring(0,9);
        boolean uuidExists = urlMapperRepository.findUrlMapperByShortenedId(uuid).isEmpty();
        Integer retries = 1;
        while (!uuidExists){
            uuid = UUID.randomUUID().toString().substring(0,9);
            uuidExists = urlMapperRepository.findUrlMapperByShortenedId(uuid).isEmpty();
            retries++;
        }
        return urlMapperRepository.save(new UrlMapper(dto.getUrl(), uuid, retries));
    }

    public String getOriginalUrl(String shortId){
        return urlMapperRepository.findUrlMapperByShortenedId(shortId).orElseThrow(() -> new RuntimeException("Short URL not found")).getOriginalUrl();
    }
}
