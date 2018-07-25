package com.yeay.shorturl.service;

import com.yeay.shorturl.entity.ShortUrlVisitRecord;

import java.util.List;

public interface ShortUrlVisitRecordService {
    void save(ShortUrlVisitRecord shortUrlVisitRecord);

    List<ShortUrlVisitRecord> findByIp(String ip);

    List<ShortUrlVisitRecord> findByShortKey(String shortKey);

    List<ShortUrlVisitRecord> findByIpAndAndShortKey(String ip, String shortKey);
}
