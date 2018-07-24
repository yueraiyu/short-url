package com.yeay.shorturl.entity;

import com.yeay.shorturl.entity.base.BaseEntity;
import com.yeay.shorturl.util.url.DigestUtil;
import com.yeay.shorturl.util.url.ShortUrlUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "short_url")
public class ShortUrl extends BaseEntity{

    @Column(name = "hash_key", nullable = false, updatable = false, unique = true)
    private String hashKey;

    @Column(name = "short_key", nullable = false, updatable = false, unique = true)
    private String shortKey;

    @Column(name = "url", nullable = false, updatable = false)
    private String url;

    public ShortUrl() {
    }

    public ShortUrl(String url) {
        super(new Date(), new Date());
        this.url = url;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
