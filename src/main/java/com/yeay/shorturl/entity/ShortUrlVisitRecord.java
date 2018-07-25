package com.yeay.shorturl.entity;


import com.yeay.shorturl.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "short_url_visit_record")
@Table(name = "short_url_visit_record",
        indexes = {@Index(name = "ip_search_index", columnList = "ip"),@Index(name = "short_key_search_index", columnList = "short_key")})
public class ShortUrlVisitRecord extends BaseEntity{
    /** 请求客户端 ip */
    @Column(name = "ip", nullable = false, updatable = false)
    private String ip;

    /** 请求短码 */
    @Column(name = "short_key", nullable = false, updatable = false)
    private String shortKey;

    /** 访问日期 */
    @Column(name = "visit_date", nullable = false, updatable = false)
    private String visitDate;

    /** 访问时间 */
    @Column(name = "visit_time", nullable = false, updatable = false)
    private String visitTime;

    /** 访问浏览器 */
    @Column(name = "browser", nullable = false, updatable = false)
    private String browser;

    /** 访问设备 */
    @Column(name = "device", nullable = false, updatable = false)
    private String device;

    /** 是否第一次访问 */
    @Column(name = "first_visit_flag", nullable = false, updatable = false)
    private Boolean firstVisitFlag;

    public ShortUrlVisitRecord() {
    }

    public ShortUrlVisitRecord(String ip, String shortKey, String visitDate, String visitTime, String browser, String device, Boolean firstVisitFlag) {
        super(new Date(), new Date());
        this.ip = ip;
        this.shortKey = shortKey;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.browser = browser;
        this.device = device;
        this.firstVisitFlag = firstVisitFlag;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Boolean getFirstVisitFlag() {
        return firstVisitFlag;
    }

    public void setFirstVisitFlag(Boolean firstVisitFlag) {
        this.firstVisitFlag = firstVisitFlag;
    }
}
