package com.yeay.shorturl.controller;

import com.yeay.shorturl.dao.ShortUrlRepository;
import com.yeay.shorturl.dao.ShortUrlVisitRecordRepository;
import com.yeay.shorturl.entity.ShortUrl;
import com.yeay.shorturl.entity.ShortUrlVisitRecord;
import com.yeay.shorturl.request.ShortUrlRequest;
import com.yeay.shorturl.util.url.DigestUtil;
import com.yeay.shorturl.util.url.RandomUtil;
import com.yeay.shorturl.util.url.RequestUtil;
import com.yeay.shorturl.util.url.ShortUrlUtil;
import com.yeay.shorturl.vo.BaseListResponseVo;
import com.yeay.shorturl.vo.BaseResponseVo;
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
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private ShortUrlVisitRecordRepository shortUrlVisitRecordRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletResponse response, Model model) {
        model.addAttribute("serverName", env.getProperty("app.server.serverName"));
        return "/index";
    }

    @RequestMapping(value = "/{shortKey}", method = RequestMethod.GET)
    public void redirect(@PathVariable(name = "shortKey") String shortKey, HttpServletRequest request, HttpServletResponse response) {
        String ip = RequestUtil.getIpAddress(request);
        List<ShortUrlVisitRecord> shortUrlVisitRecords = shortUrlVisitRecordRepository.findByIp(ip);
        Boolean firstVisitFlag = CollectionUtils.isEmpty(shortUrlVisitRecords) ? true : false;
        String os = RequestUtil.getOsInfo(request);
        String browser = RequestUtil.getBrowserInfo(request);
        ShortUrlVisitRecord shortUrlVisitRecord = new ShortUrlVisitRecord(ip, shortKey, new Date(), browser, os, firstVisitFlag);
        shortUrlVisitRecordRepository.save(shortUrlVisitRecord);

        ShortUrl shortUrl = shortUrlRepository.findByShortKey(shortKey);
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
            baseResponseVo.setMsg("url is empty!");
            return baseResponseVo;
        }

        baseResponseVo = saveShortUrl(new ShortUrl(url), shortKey);

        return baseResponseVo;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    BaseListResponseVo<ShortUrl> list(Integer page, Integer limit, HttpServletRequest request, Model model) {
        BaseListResponseVo<ShortUrl> responseVo = new BaseListResponseVo<>();
        List<ShortUrl> shortUrls = new ArrayList<>();
        responseVo.setCode(0);
        responseVo.setMsg("");
        responseVo.setCount(1000L);
        responseVo.setData(shortUrls);
        return responseVo;
    }

    private BaseResponseVo<String> saveShortUrl(ShortUrl shortUrl, String shortKey) {
        BaseResponseVo<String> baseResponseVo = new BaseResponseVo<>();
        String serverName = env.getProperty("app.server.serverName");
        String hashKey = DigestUtil.getSha1Str(shortUrl.getUrl());

        ShortUrl findByHashKey = shortUrlRepository.findByHashKey(hashKey);

        // hashKey已存在
        if (findByHashKey != null){
            // 同一url
            if(shortUrl.getUrl().equals(findByHashKey.getUrl())){
                baseResponseVo.setCode(201);
                baseResponseVo.setMsg("url has exist!");
                baseResponseVo.setData(serverName + findByHashKey.getShortKey());

                return baseResponseVo;
            }
            // 非同一url 二次hash
            else{
                shortUrl.setHashKey(DigestUtil.getSha1Str(hashKey));
            }
        }else {
            shortUrl.setHashKey(DigestUtil.getSha1Str(shortUrl.getUrl()));
        }

        if (!StringUtils.isEmpty(shortKey)){
            ShortUrl findByShortKey = shortUrlRepository.findByShortKey(shortKey);

            // shortKey已存在
            if (findByShortKey != null){
                baseResponseVo.setCode(400);
                baseResponseVo.setMsg("duplicate short key!");

                return baseResponseVo;
            }

            shortUrl.setShortKey(shortKey);
        }else {
            shortUrl.setShortKey(ShortUrlUtil.compression(shortUrl.getUrl()));
        }

        shortUrlRepository.save(shortUrl);

        baseResponseVo.setCode(200);
        baseResponseVo.setMsg("success");
        baseResponseVo.setData(serverName + shortUrl.getShortKey());

        return baseResponseVo;
    }
}
