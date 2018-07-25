package com.yeay.shorturl.service.impl;

import com.yeay.shorturl.dao.ShortUrlVisitRecordRepository;
import com.yeay.shorturl.entity.ShortUrlVisitRecord;
import com.yeay.shorturl.service.ShortUrlVisitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("shortUrlVisitRecordService")
public class ShortUrlVisitRecordServiceImpl implements ShortUrlVisitRecordService{
    @Autowired
    private ShortUrlVisitRecordRepository shortUrlVisitRecordRepository;

    @Override
    @Transient
    public void save(ShortUrlVisitRecord shortUrlVisitRecord) {
        shortUrlVisitRecordRepository.save(shortUrlVisitRecord);
    }

    @Override
    public List<ShortUrlVisitRecord> findByIp(String ip) {
        return shortUrlVisitRecordRepository.findByIp(ip);
    }

    @Override
    public List<ShortUrlVisitRecord> findByShortKey(String shortKey) {
        return shortUrlVisitRecordRepository.findByShortKey(shortKey);
    }

    @Override
    public List<ShortUrlVisitRecord> findByIpAndAndShortKey(String ip, String shortKey) {
        return shortUrlVisitRecordRepository.findByIpAndAndShortKey(ip, shortKey);
    }
}
