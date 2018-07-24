package com.yeay.shorturl.dao;

import com.yeay.shorturl.entity.ShortUrlVisitRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShortUrlVisitRecordRepository extends CrudRepository<ShortUrlVisitRecord, Long> {

    List<ShortUrlVisitRecord> findByIp(String ip);

    List<ShortUrlVisitRecord> findByShortKey(String shortKey);
}
