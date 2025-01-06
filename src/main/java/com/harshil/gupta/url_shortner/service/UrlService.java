package com.harshil.gupta.url_shortner.service;

import com.harshil.gupta.url_shortner.dao.UrlRepository;
import com.harshil.gupta.url_shortner.model.Url;
import com.harshil.gupta.url_shortner.record.AnalyticsResponse;
import com.harshil.gupta.url_shortner.util.Base62Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@EnableScheduling
public class UrlService {

    private static final HashMap<String, Long> urlCountMap = new HashMap<>();
    @Autowired
    private UrlRepository urlRepository;

    /**
     * Generate short URL based on the timestamp (current time in milliseconds)
     *
     * @param longUrl long url giver by user
     * @return shortUrl
     */
    public String createShortUrl(String longUrl) {

        // Generate the timestamp-based ID (current time in milliseconds)
        long timestamp = System.currentTimeMillis();

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setClickCount(0);

        // Use the timestamp to generate the short URL
        String shortUrl = Base62Encoder.encode(timestamp);

        url.setShortUrl(shortUrl);

        // Save the URL entity to the database
        urlRepository.save(url);

        return shortUrl;
    }

    @Cacheable(value = "shortUrlCache", key = "#shortUrl")
    public Url getLongUrl(String shortUrl) {
//        log.info("DB Call");
        return urlRepository.findByShortUrl(shortUrl).orElse(null);
    }

    /**
     * Increment click count for the short URL
     *
     * @param url
     * @return
     */
    public void incrementClickCount(Url url) {
        String shortUrl = url.getShortUrl();
        if (!urlCountMap.containsKey(shortUrl)) {
            urlCountMap.put(shortUrl, url.getClickCount() + 1);
        } else {
            urlCountMap.put(shortUrl, urlCountMap.get(shortUrl) + 1);
        }

    }

    /**
     * Clear the HashMap every 10 minutes
     */
    @Scheduled(fixedRate = 600000)
    public void clearUrlCountMap() {
//        log.info("Running Thread");

        for (Map.Entry<String, Long> entry : urlCountMap.entrySet()) {
//            log.info("short Url " + entry.getKey() + " Click Count " + entry.getValue());
            Url url = urlRepository.findByShortUrl(entry.getKey()).get();
            url.setClickCount(url.getClickCount() + entry.getValue());
            urlRepository.save(url);
            urlCountMap.remove(entry.getKey());
        }
    }

    /**
     * Give Analytics for Clicked count.
     *
     * @return String result
     */
    public List<AnalyticsResponse> getAnalytics() {
        return urlRepository.findTop5Urls()
                .stream()
                .map(url -> new AnalyticsResponse(url))
                .toList();
    }
}
