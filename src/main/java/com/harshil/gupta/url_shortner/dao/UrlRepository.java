package com.harshil.gupta.url_shortner.dao;

import com.harshil.gupta.url_shortner.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url,Long> {
    Optional<Url> findByShortUrl(String shortUrl);

    @Query(value = "SELECT * FROM Url ORDER BY click_count DESC LIMIT 5", nativeQuery = true)
    List<Url> findTop5Urls();
}
