package com.yeay.shorturl.controller;

import com.yeay.shorturl.entity.ShortUrl;
import com.yeay.shorturl.entity.ShortUrlVisitRecord;
import com.yeay.shorturl.request.ShortUrlRequest;
import com.yeay.shorturl.request.ShortUrlVisitRecordRequest;
import com.yeay.shorturl.service.ShortUrlService;
import com.yeay.shorturl.service.ShortUrlVisitRecordService;
import com.yeay.shorturl.util.datetime.DateTimeUtil;
import com.yeay.shorturl.util.url.ClientInfoUtil;
import com.yeay.shorturl.vo.BaseListResponseVo;
import com.yeay.shorturl.vo.BaseResponseVo;
import com.yeay.shorturl.vo.ChartsBaseDataVo;
import com.yeay.shorturl.vo.ChartsResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ShortUrlController {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    @Autowired
    private Environment env;

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private ShortUrlVisitRecordService shortUrlVisitRecordService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletResponse response, Model model) {
        model.addAttribute("serverName", env.getProperty("app.server.serverName"));
        return "/index";
    }

    @RequestMapping(value = "/{shortKey}", method = RequestMethod.GET)
    public void redirect(@PathVariable(name = "shortKey") String shortKey, HttpServletRequest request, HttpServletResponse response) {
        String ip = ClientInfoUtil.getIpAddress(request);
        List<ShortUrlVisitRecord> shortUrlVisitRecords = shortUrlVisitRecordService.findByIpAndAndShortKey(ip, shortKey);
        Boolean firstVisitFlag = CollectionUtils.isEmpty(shortUrlVisitRecords) ? true : false;
        String os = ClientInfoUtil.getOsInfo(request);
        String browser = ClientInfoUtil.getBrowserInfo(request);
        ShortUrlVisitRecord shortUrlVisitRecord = new ShortUrlVisitRecord(ip, shortKey, new Date(), browser, os, firstVisitFlag);
        shortUrlVisitRecordService.save(shortUrlVisitRecord);

        ShortUrl shortUrl = shortUrlService.findByShortKey(shortKey);
        response.setStatus(302);
        response.setHeader("Location", shortUrl.getUrl());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    BaseResponseVo<String> save(@RequestBody ShortUrlRequest shortUrlRequest, HttpServletResponse response){
        String url = shortUrlRequest != null ? shortUrlRequest.getUrl() : null;
        String shortKey = shortUrlRequest != null ? shortUrlRequest.getShortKey() : null;
        logger.info("save url , url : {}, short key : {}", url, shortKey);

        //校验 url 是否有值, 否则返回错误信息
        BaseResponseVo<String> baseResponseVo = new BaseResponseVo<>();
        if (StringUtils.isEmpty(url)){
            baseResponseVo.setCode(400);
            baseResponseVo.setMsg("输入请求地址为空!");
            return baseResponseVo;
        }

        baseResponseVo = shortUrlService.save(new ShortUrl(url), shortKey);

        return baseResponseVo;
    }

    @RequestMapping(value = "/charts", method = RequestMethod.POST)
    public @ResponseBody
    ChartsResponseVo charts(@RequestBody ShortUrlVisitRecordRequest shortUrlVisitRecordRequest, Model model) {

        ChartsResponseVo response = new ChartsResponseVo();

        List<ShortUrlVisitRecord> shortUrlVisitRecords = shortUrlVisitRecordService
                .findByShortKeyAndAndVisitTimeBetween(shortUrlVisitRecordRequest.getSearchKey(),
                        shortUrlVisitRecordRequest.getStartTime(), shortUrlVisitRecordRequest.getEndTime());

        //title shortKey + 访问统计, shortKey + 访问分类
        List<String> titles = new ArrayList<>(2);
        titles.add((StringUtils.isEmpty(shortUrlVisitRecordRequest.getSearchKey()) ? "" : shortUrlVisitRecordRequest.getSearchKey()) + "访问统计");
        titles.add((StringUtils.isEmpty(shortUrlVisitRecordRequest.getSearchKey()) ? "" : shortUrlVisitRecordRequest.getSearchKey()) + "访问分类");
        response.setTitles(titles);

        //xAxis data 时间
        List<String> dates = DateTimeUtil.getDateStringBetween(shortUrlVisitRecordRequest.getStartTime(), shortUrlVisitRecordRequest.getEndTime());
        response.setDates(dates);

        //yAxis 访问总数(数组) 新增IP（数组） 短码（数组）
        List<Long> visitCounts = new ArrayList<>(dates.size());
        List<Long> visitIpCounts = new ArrayList<>(dates.size());
        List<Long> visitShortKeyCounts = new ArrayList<>(dates.size());
        for (String dateStr:dates) {
            Date date = DateTimeUtil.getDateFormString(dateStr, null);

            List<ShortUrlVisitRecord> dateGroup = shortUrlVisitRecords.stream()
                    .filter(shortUrlVisitRecord -> DateTimeUtil.isSameDay(date, shortUrlVisitRecord.getVisitTime()))
                    .collect(Collectors.toList());

            Map<String, List<ShortUrlVisitRecord>> ipGroup = dateGroup.stream()
                    .collect(Collectors.groupingBy(ShortUrlVisitRecord :: getIp));

            Map<String, List<ShortUrlVisitRecord>> shortKeyGroup = dateGroup.stream()
                    .collect(Collectors.groupingBy(ShortUrlVisitRecord :: getShortKey));

            visitCounts.add((long) dateGroup.size());
            visitIpCounts.add((long) ipGroup.size());
            visitShortKeyCounts.add((long) shortKeyGroup.size());
        }
        response.setVisitCounts(visitCounts);
        response.setVisitIpCounts(visitIpCounts);
        response.setVisitShortKeyCounts(visitShortKeyCounts);

        //饼图数据 系统类型-数量（数组），浏览器类型-数量（数组）
        Map<String, Long> deviceGroup = shortUrlVisitRecords.stream()
                .collect(Collectors.groupingBy(ShortUrlVisitRecord :: getDevice,Collectors.counting()));
        List<ChartsBaseDataVo> devices = deviceGroup.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
                .map(e -> new ChartsBaseDataVo(e.getKey(), String.valueOf(e.getValue()))).collect(Collectors.toList());
        response.setDevices(devices);

        Map<String, Long> browserGroup = shortUrlVisitRecords.stream()
                .collect(Collectors.groupingBy(ShortUrlVisitRecord :: getBrowserType,Collectors.counting()));
        List<ChartsBaseDataVo> browsers = browserGroup.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
                .map(e -> new ChartsBaseDataVo(e.getKey(), String.valueOf(e.getValue()))).collect(Collectors.toList());
        response.setBrowsers(browsers);

        return response;
    }
}
