package com.yeay.shorturl.controller;

import com.yeay.shorturl.dao.ShortUrlRepository;
import com.yeay.shorturl.entity.ShortUrl;
import com.yeay.shorturl.request.ShortUrlRequest;
import com.yeay.shorturl.util.url.DigestUtil;
import com.yeay.shorturl.util.url.ShortUrlUtil;
import com.yeay.shorturl.vo.BaseResponse;
import com.yeay.shorturl.vo.BaseListResponseVo;
import com.yeay.shorturl.vo.BaseResponseVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private ShortUrlRepository shortUrlRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletResponse response) {
        return "/index";
    }

    @RequestMapping(value = "/{shortKey}", method = RequestMethod.GET)
    public void redirect(@PathVariable(name = "shortKey") String shortKey, HttpServletResponse response) {
        ShortUrl shortUrl = shortUrlRepository.findByShortKey(shortKey);
        response.setStatus(302);
        response.setHeader("Location", shortUrl.getUrl());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    BaseResponseVo<String> save(@RequestBody ShortUrlRequest shortUrlRequest, HttpServletResponse response){
        logger.info("save url , url :{}", shortUrlRequest.getUrl());
        String url = shortUrlRequest != null ? shortUrlRequest.getUrl() : null;

        BaseResponseVo<String> baseResponseVo = new BaseResponseVo<>();
        if (StringUtils.isEmpty(url)){
            baseResponseVo.setCode(400);
            baseResponseVo.setMsg("url is empty!");
            return baseResponseVo;
        }
        String hashKey = DigestUtil.getSha1Str(url);

        ShortUrl existUrl = shortUrlRepository.findByHashKey(hashKey);
        if (existUrl != null){
            baseResponseVo.setCode(200);
            baseResponseVo.setMsg("success");
            baseResponseVo.setData(ShortUrlUtil.getShortUrl(existUrl.getShortKey()));

            return baseResponseVo;
        }

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setHashKey(hashKey);
        shortUrl.setShortKey(ShortUrlUtil.compression(url));
        shortUrl.setUrl(url);
        shortUrl.setModifyDate(new Date());
        shortUrl.setCreateDate(new Date());
        shortUrl.setClickNum(0L);
        shortUrlRepository.save(shortUrl);

        baseResponseVo.setCode(200);
        baseResponseVo.setMsg("success");
        baseResponseVo.setData(ShortUrlUtil.getShortUrl(shortUrl.getShortKey()));

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
}
