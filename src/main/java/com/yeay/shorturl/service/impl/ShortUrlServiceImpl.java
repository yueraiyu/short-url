package com.yeay.shorturl.service.impl;

import com.yeay.shorturl.dao.ShortUrlRepository;
import com.yeay.shorturl.entity.ShortUrl;
import com.yeay.shorturl.service.ShortUrlService;
import com.yeay.shorturl.util.url.DigestUtil;
import com.yeay.shorturl.util.url.ShortUrlUtil;
import com.yeay.shorturl.vo.BaseResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("shortUrlService")
public class ShortUrlServiceImpl implements ShortUrlService{

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private Environment env;

    @Override
    @Transient
    public void save(ShortUrl shortUrl) {
        shortUrlRepository.save(shortUrl);
    }

    @Override
    public ShortUrl findByHashKey(String hashKey) {
        return shortUrlRepository.findByHashKey(hashKey);
    }

    @Override
    public ShortUrl findByShortKey(String shortKey) {
        return shortUrlRepository.findByShortKey(shortKey);
    }

    @Override
    public BaseResponseVo<String> save(ShortUrl shortUrl, String shortKey) {
        BaseResponseVo<String> baseResponseVo = new BaseResponseVo<>();
        String serverName = env.getProperty("app.server.serverName");
        String hashKey = DigestUtil.getSha1Str(shortUrl.getUrl());

        ShortUrl findByHashKey = findByHashKey(hashKey);

        // hashKey已存在
        if (findByHashKey != null){
            // 同一url
            if(shortUrl.getUrl().equals(findByHashKey.getUrl())){
                baseResponseVo.setCode(201);
                baseResponseVo.setMsg("输入请求地址已存在!");
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
            ShortUrl findByShortKey = findByShortKey(shortKey);

            // shortKey已存在
            if (findByShortKey != null){
                baseResponseVo.setCode(400);
                baseResponseVo.setMsg("重复的自定义键值!");

                return baseResponseVo;
            }

            shortUrl.setShortKey(shortKey);
        }else {
            shortUrl.setShortKey(ShortUrlUtil.compression(shortUrl.getUrl()));
        }

        save(shortUrl);

        baseResponseVo.setCode(200);
        baseResponseVo.setMsg("转换成功");
        baseResponseVo.setData(serverName + shortUrl.getShortKey());

        return baseResponseVo;
    }
}
