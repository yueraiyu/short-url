package com.yeay.shorturl.vo;

import java.util.List;

public class ChartsResponseVo extends BaseResponse {
    private List<String> titles;

    private List<String> dates;

    private List<Long> visitCounts;

    private List<Long> visitIpCounts;

    private List<Long> visitShortKeyCounts;

    private List<ChartsBaseDataVo> devices;

    private List<ChartsBaseDataVo> browsers;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Long> getVisitCounts() {
        return visitCounts;
    }

    public void setVisitCounts(List<Long> visitCounts) {
        this.visitCounts = visitCounts;
    }

    public List<Long> getVisitIpCounts() {
        return visitIpCounts;
    }

    public void setVisitIpCounts(List<Long> visitIpCounts) {
        this.visitIpCounts = visitIpCounts;
    }

    public List<Long> getVisitShortKeyCounts() {
        return visitShortKeyCounts;
    }

    public void setVisitShortKeyCounts(List<Long> visitShortKeyCounts) {
        this.visitShortKeyCounts = visitShortKeyCounts;
    }

    public List<ChartsBaseDataVo> getDevices() {
        return devices;
    }

    public void setDevices(List<ChartsBaseDataVo> devices) {
        this.devices = devices;
    }

    public List<ChartsBaseDataVo> getBrowsers() {
        return browsers;
    }

    public void setBrowsers(List<ChartsBaseDataVo> browsers) {
        this.browsers = browsers;
    }
}
