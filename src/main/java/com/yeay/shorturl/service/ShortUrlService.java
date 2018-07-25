package com.yeay.shorturl.service;

import com.yeay.shorturl.entity.ShortUrl;
import com.yeay.shorturl.vo.BaseResponseVo;

public interface ShortUrlService {
    void save(ShortUrl shortUrl);

    BaseResponseVo<String> save(ShortUrl shortUrl, String shortKey);

    ShortUrl findByHashKey(String hashKey);

    ShortUrl findByShortKey(String shortKey);
}
