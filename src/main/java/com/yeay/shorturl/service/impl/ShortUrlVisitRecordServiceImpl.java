package com.yeay.shorturl.service.impl;

import com.yeay.shorturl.dao.ShortUrlVisitRecordRepository;
import com.yeay.shorturl.entity.ShortUrlVisitRecord;
import com.yeay.shorturl.service.ShortUrlVisitRecordService;
import com.yeay.shorturl.util.datetime.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @Override
    public List<ShortUrlVisitRecord> findByShortKeyAndAndVisitTimeBetween(String shortKey, Date startTime, Date endTime) {
        Specification querySpecifi = new Specification<ShortUrlVisitRecord>() {
            @Override
            public Predicate toPredicate(Root<ShortUrlVisitRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(!StringUtils.isEmpty(shortKey)){
                    predicates.add(criteriaBuilder.equal(root.get("shortKey"), shortKey));
                }

                if(null != startTime){
                    Calendar start = DateTimeUtil.getDate(startTime);
                    predicates.add(criteriaBuilder.greaterThan(root.get("visitTime"), start.getTime()));
                }

                if(null != endTime){
                    Calendar end = DateTimeUtil.getDate(endTime);
                    end.add(Calendar.DAY_OF_MONTH, 1);
                    predicates.add(criteriaBuilder.lessThan(root.get("visitTime"), end.getTime()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return  this.shortUrlVisitRecordRepository.findAll(querySpecifi);


//        if (StringUtils.isEmpty(shortKey))
//            return shortUrlVisitRecordRepository.findAll();
//
//        if(startTime == null)
//            return shortUrlVisitRecordRepository.findByShortKey(shortKey);
//
//        if (endTime == null)
//            endTime = new Date();
//
//        return shortUrlVisitRecordRepository.findByShortKeyAndAndVisitTimeBetween(shortKey, startTime, endTime);
    }
}
