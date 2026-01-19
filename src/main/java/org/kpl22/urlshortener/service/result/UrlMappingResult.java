package org.kpl22.urlshortener.service.result;

import org.kpl22.urlshortener.entity.UrlMapper;

public final class UrlMappingResult {

    private final UrlMapper urlMapper;
    private final boolean created;

    public UrlMappingResult(UrlMapper urlMapper, boolean created) {
        this.urlMapper = urlMapper;
        this.created = created;
    }

    public UrlMapper getUrlMapper() {
        return urlMapper;
    }

    public boolean isCreated() {
        return created;
    }
}
