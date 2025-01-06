package com.harshil.gupta.url_shortner.controller;

import com.harshil.gupta.url_shortner.model.Url;
import com.harshil.gupta.url_shortner.record.AnalyticsResponse;
import com.harshil.gupta.url_shortner.service.UrlService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/url")
@Slf4j
public class UrlController {

    @Autowired
    private UrlService urlService;

    /**
     * Shorten a URL
     *
     * @param longUrl user input long url
     * @return ResponseEntity<String>
     */
    @PostMapping(value = "/shorten", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> shortenUrl(@RequestParam String longUrl) {
        String shortUrl = urlService.createShortUrl(longUrl);
        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    /**
     * Redirect to Original Url
     *
     * @param shortUrl user input short url
     * @param response HttpServletResponse
     * @return void
     */
    @GetMapping(value = "/{shortUrl}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RateLimiter(name = "shortLinkLimiter", fallbackMethod = "handleShortUrl")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl, HttpServletResponse response) {
        try {
            Url url = urlService.getLongUrl(shortUrl);
            String longUrl = url.getLongUrl();

            if (longUrl == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            urlService.incrementClickCount(url);
            response.sendRedirect(longUrl);
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(value = "/analytics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnalyticsResponse>> getAnalytics() {
        return new ResponseEntity<>(urlService.getAnalytics(), HttpStatus.OK);
    }

    public ResponseEntity<String> handleShortUrl(Exception ex) {
        return ResponseEntity.ok("URL Limit Exceed, Please try after sometime");
    }
}
