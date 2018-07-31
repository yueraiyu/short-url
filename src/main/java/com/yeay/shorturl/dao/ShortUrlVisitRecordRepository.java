package com.yeay.shorturl.dao;

import com.yeay.shorturl.entity.ShortUrlVisitRecord;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ShortUrlVisitRecordRepository extends CrudRepository<ShortUrlVisitRecord, Long> {

    List<ShortUrlVisitRecord> findByIp(String ip);

    List<ShortUrlVisitRecord> findByShortKey(String shortKey);

    List<ShortUrlVisitRecord> findByIpAndAndShortKey(String ip, String shortKey);

    @Override
    List<ShortUrlVisitRecord> findAll();

    List<ShortUrlVisitRecord> findAll(Specification querySpecifi);

}
