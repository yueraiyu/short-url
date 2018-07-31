package com.yeay.shorturl.util.url;

import org.apache.commons.lang.math.RandomUtils;
import java.util.Random;

public class RandomUtil extends RandomUtils {

    private static final char[] CODE_SEQ = { 'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z' , '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' ,
            'I' , 'J' , 'K' , 'L' , 'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' ,
            'U' , 'V' , 'W' , 'X' , 'Y' , 'Z'};

    private static Random random = new Random();

    /**
     * 随机生成指定长度字符
     * @param length
     * @return
     */
    public static final String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(CODE_SEQ[random.nextInt(CODE_SEQ.length)]));
        }
        return sb.toString();
    }

    public static char getCodeByIndex(Long index) {
        if (index < 0 || index > CODE_SEQ.length) {
            throw new IndexOutOfBoundsException();
        }

        return CODE_SEQ[index.intValue()];
    }
}
