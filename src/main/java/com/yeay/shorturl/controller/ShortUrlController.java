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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        ShortUrlVisitRecord shortUrlVisitRecord = new ShortUrlVisitRecord(ip, shortKey, DateTimeUtil.getCurrentDateString(),
                DateTimeUtil.getCurrentTimeString(), browser, os, firstVisitFlag);
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

    @RequestMapping(value = "/charts", method = RequestMethod.GET)
    public @ResponseBody
    ChartsResponseVo charts(ShortUrlVisitRecordRequest request, Model model) {
        String shortKey = request.getShortKey();
        Date startDate = request.getStartTime();
        Date endDate = request.getEndTime();
        ChartsResponseVo response = new ChartsResponseVo();
        //title shortKey + 访问统计, shortKey + 访问分类
        List<String> titles = new ArrayList<>(2);
        titles.add(shortKey + "-访问统计");
        titles.add(shortKey + "-访问分类");
        response.setTitles(titles);

        //xAxis data 时间
        List<String> dates = shortUrlVisitRecordService.groupVisitTime(shortKey);

        //yAxis 访问总数(数组) 新增IP（数组） 短码（数组）

        //饼图数据 系统类型-数量（数组），浏览器类型-数量（数组）
        return null;
    }
}
