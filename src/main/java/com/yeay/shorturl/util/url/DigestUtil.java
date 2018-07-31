package com.yeay.shorturl.util.url;

import org.apache.commons.codec.binary.Hex;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
public class DigestUtil {

	private static final String SHA1 = "SHA-1";

	private static final String MD5 = "MD5";

	/**
	 * 创建一个新的实例 MD5Util.
	 * 
	 */
	private DigestUtil() {

	}

	/**
	 * 获取安全哈希值
	 * @param sourceStr
	 * @return
	 */
	public static String getSha1Str(String sourceStr){
		if (sourceStr == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
			messageDigest.update(sourceStr.getBytes());
			return Hex.encodeHexString(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * md5加密方法 默认UTF-8编码
	 *
	 * @param sourceStr
	 * @return
	 */
	public static String getMD5Str(String sourceStr) {
		return getMD5Str(sourceStr, "UTF-8");
	}

	/**
	 * md5加密方法
	 *
	 * @param sourceStr 需要加密的信息
	 * @param charSet 试用的编码格式
	 * @return
	 */
	public static String getMD5Str(String sourceStr, String charSet) {
		if (sourceStr == null) {
			return null;
		}

		String result = "";

		try {
			MessageDigest md = MessageDigest.getInstance(MD5);

			try {
				md.update(sourceStr.getBytes(charSet));
			} catch (UnsupportedEncodingException e) {
				md.update(sourceStr.getBytes());

			}
			byte[] md5Bytes = md.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < md5Bytes.length; offset++) {
				i = md5Bytes[offset];
				if (i < 0) {
					i += 256;
				}

				if (i < 16) {
					buf.append("0");
				}

				buf.append(Integer.toHexString(i));
			}

			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}
}
