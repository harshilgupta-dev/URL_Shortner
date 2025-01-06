package com.harshil.gupta.url_shortner.record;

import com.harshil.gupta.url_shortner.model.Url;

public record AnalyticsResponse(String shortUrl, String longUrl, long clickCount) {

    public AnalyticsResponse(Url url){
        this(url.getShortUrl(),url.getLongUrl(),url.getClickCount());
    }
}
