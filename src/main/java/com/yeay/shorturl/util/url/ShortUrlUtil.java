package com.yeay.shorturl.util.url;

import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * 生成短url工具
 */
public class ShortUrlUtil {

    /** 随机key 长度*/
    private static final int RANDOM_KEY_LENGTH = 7;

    /** 30位有效长度位运算*/
    private static final String VALID_LENGTH_OPERATION = "3FFFFFFF";

    /** 16进制*/
    private static final int HEX_FLAG = 16;

    /** 2进制*/
    private static final int BINARY_FLAG = 2;

    /** 获取 < 61 的有效数位运算*/
    private static final String VALID_INDEX_OPERATION = "0000003D";

    /**
     * 压缩url
     * @param url
     * @return
     */
    public static String compression(String url){
        if (StringUtils.isEmpty(url))
            return null;
        // step1: 将原 url + 随机 key , 生成 32 位 md5 随机串
        String md5Str = getMD5Str(url);

        // step2: 将 md5 随机串均分四段
        String[] md5Sections = splitMd5Str(md5Str);

        // step3: 将 md5Sections 转换为对应的短连接码
        return convertSections(md5Sections);
    }

    public static String appendRandomCharUrl(String url){
        if (StringUtils.isEmpty(url))
            return null;
        return getAppendWithRandomCharUrl(url);
    }

    private static String convertSections(String[] md5Sections) {
        String[] shorKeys = new String[md5Sections.length];
        for (int i = 0; i < md5Sections.length; i ++){
            // step1: 将对应截取段作为 16 进制数字处理，并转换为 Long
            Long section = Long.valueOf(md5Sections[i], HEX_FLAG);

            // step2: 将转换后的数字转换为二进制
            String binarySection = Long.toBinaryString(section);

            // step3: Long 二进制有效长度为 32 位， 需要将前面两位去掉，留下30位 （30位才能转换62进制，否则超出）， 通过 & 0x3fffffff 进行位与运算
            Long validSection = section & Long.valueOf(VALID_LENGTH_OPERATION, HEX_FLAG);

            // step4: 与 0x0000003D 进行 6 次位运算，每次位移 >> 5, 通过 & 0x0000003D 进行位与运算得到一个 <= 61 的数字，作为数组[a-zA-Z0-9]的下标，转换为相应短码
            String sectionShortKey = getShortKey(validSection);

            shorKeys[i] = sectionShortKey;
        }

        // step5: 随机获取一个 短码
        return shorKeys[new Random().nextInt(md5Sections.length)];
    }

    private static String getShortKey(Long validSection) {
        StringBuilder shortKey = new StringBuilder();
        for (int i = 0; i < 6; i ++){
            Long index = validSection & Long.valueOf(VALID_INDEX_OPERATION, HEX_FLAG);
            shortKey.append(RandomUtil.getCodeByIndex(index));
            validSection >>= 5;
        }

        return shortKey.toString();
    }

    private static String[] splitMd5Str(String md5Str) {
        String[] sections = new String[4];
        for (int i = 0; i < 4; i ++){
            int start = i * 8;
            int end = (i + 1) * 8;
            sections[i] = md5Str.substring(start, end);
        }
        return sections;
    }

    private static String getMD5Str(String url) {
        String appendUrl = getAppendWithRandomCharUrl(url);
        String md5Str = DigestUtil.getMD5Str(appendUrl);
        return md5Str;
    }

    private static String getAppendWithRandomCharUrl(String url) {
        String randomKey = RandomUtil.randomString(RANDOM_KEY_LENGTH);
        String appendUrl = url + randomKey;
        return appendUrl;
    }
}
