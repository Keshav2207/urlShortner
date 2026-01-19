package org.kpl22.urlshortener.controller;

import org.kpl22.urlshortener.dto.UrlRequestDTO;
import org.kpl22.urlshortener.dto.UrlResponseDTO;
import org.kpl22.urlshortener.service.UrlMapperService;
import org.kpl22.urlshortener.service.result.UrlMappingResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UrlShortenerController {

    private final UrlMapperService urlMapperService;

    public UrlShortenerController(UrlMapperService urlMapperService) {
        this.urlMapperService = urlMapperService;
    }

    @PostMapping("/url/shorten")
    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestBody UrlRequestDTO request){
        UrlMappingResult result = urlMapperService.createUrlMapping(request);
        String shortId = result.getUrlMapper().getShortenedId();
        HttpStatus status = result.isCreated() ? HttpStatus.CREATED : HttpStatus.OK;
        String shortUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").path(shortId).toUriString();
        return ResponseEntity.status(status).body(new UrlResponseDTO(shortUrl));
    }

    @GetMapping("/{shortenedId}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortenedId){
        String originalUrl = urlMapperService.getOriginalUrl(shortenedId);
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, originalUrl).build();
    }
}
