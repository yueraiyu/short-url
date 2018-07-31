package com.yeay.shorturl.service;

import com.yeay.shorturl.entity.ShortUrlVisitRecord;

import java.util.Date;
import java.util.List;

public interface ShortUrlVisitRecordService {
    /**
     * 保存短码点击记录
     * @param shortUrlVisitRecord
     */
    void save(ShortUrlVisitRecord shortUrlVisitRecord);

    /**
     * 通过IP查找点击记录
     * @param ip
     * @return
     */
    List<ShortUrlVisitRecord> findByIp(String ip);

    /**
     * 通过短码查找访问记录
     * @param shortKey
     * @return
     */
    List<ShortUrlVisitRecord> findByShortKey(String shortKey);

    /**
     * 通过短码及IP查找访问记录
     * @param ip
     * @param shortKey
     * @return
     */
    List<ShortUrlVisitRecord> findByIpAndAndShortKey(String ip, String shortKey);

    /**
     * 通过短码、时间范围查找访问记录
     * @param shortKey
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShortUrlVisitRecord> findByShortKeyAndAndVisitTimeBetween(String shortKey, Date startTime, Date endTime);
}
