package com.yeay.shorturl.dao;

import com.yeay.shorturl.entity.ShortUrl;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, Long> {

    ShortUrl findByHashKey(String hashKey);

    ShortUrl findByShortKey(String shortKey);
}
