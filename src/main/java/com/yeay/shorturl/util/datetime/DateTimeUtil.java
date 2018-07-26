package com.yeay.shorturl.util.datetime;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Yeager
 *
 * 日期工具类
 * java8 java.time包实现
 */
public class DateTimeUtil extends DateUtils{

	public static final String DEFAULT_DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMATTER = "yyyyMMdd";

	public static final String DATE_FORMATTER_EN = "yyyy-MM-dd";

    public static final String DATE_SPLIT = "-";

    public static final String TIME_SPLIT = ":";

	/**
	 * 根据格式获取指定日期格式的字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}

		if (StringUtils.isEmpty(pattern)){
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMATTER_EN);
			return format.format(date);
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getCurrentDateString(){
		Calendar ca = Calendar.getInstance();

		int year = ca.get(Calendar.YEAR);//获取年份
		int month=ca.get(Calendar.MONTH);//获取月份
		int day=ca.get(Calendar.DATE);//获取日

		return year + DATE_SPLIT +month + DATE_SPLIT + day;
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTimeString(){
		Calendar ca = Calendar.getInstance();

		int hour=ca.get(Calendar.HOUR);//小时
		int minute=ca.get(Calendar.MINUTE);//分
		int second=ca.get(Calendar.SECOND);//秒

		return hour + TIME_SPLIT + minute + TIME_SPLIT + second;
	}

	/**
	 * 获取日期
	 * @param formatter 格式化
	 * @return String
	 */
	public static Date getDateFormString(String dateStr, SimpleDateFormat formatter) {
		if (null == formatter) {
			formatter = new SimpleDateFormat(DATE_FORMATTER_EN);
		}

		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 获取当前日期时间字符串
	 * @param formatter 格式化
	 * @return String
	 */
	public static String getCurrentDateTimeAsString(DateTimeFormatter formatter) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		if (null == formatter) {
			formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER);
		}
		return currentDateTime.format(formatter);
	}

    /**
     * 获取当前日期字符串
     * @param formatter 格式化
     * @return String
     */
    public static String getCurrentDateAsString(DateTimeFormatter formatter) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (null == formatter) {
            formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        }
        return currentDateTime.format(formatter);
    }

	/**
	 * 获取当前日期时间
	 * @param formatter 格式化
	 * @return LocalDateTime
	 */
	public static LocalDateTime getCurrentLocalDateTime(DateTimeFormatter formatter) {
		String text = getCurrentDateTimeAsString(null);
		if (null == formatter) {
			formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMATTER);
		}
		return LocalDateTime.parse(text, formatter);
	}

	/**
	 * 获取当前日期时间
	 * @param formatter 格式化
	 * @return Date
	 */
	public static Date getCurrentDate(DateTimeFormatter formatter) {
		LocalDateTime parse = getCurrentLocalDateTime(formatter);
		return convertLocalDateTimeToDate(parse);
	}

	/**
	 * 转换java8的LocalDateTime为Date对象
	 * @param localDateTime java8
	 * @return Date
	 */
	public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime){
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 在当前时间加秒数
	 * @param second 秒(可为负数)
	 * @return 日期
	 */
	public static Date plusSecondBaseOnCurrentDate(int second) {
		LocalDateTime currentLocalDateTime = getCurrentLocalDateTime(null);
		LocalDateTime plusSeconds = currentLocalDateTime.plusSeconds(second);
		return convertLocalDateTimeToDate(plusSeconds);
	}

    /**
     * 在当前时间加分钟
     * @param min 分钟(可为负数)
     * @return 日期
     */
    public static Date plusMinBaseOnCurrentDate(int min) {
        LocalDateTime currentLocalDateTime = getCurrentLocalDateTime(null);
        LocalDateTime plusMin = currentLocalDateTime.plusMinutes(min);
        return convertLocalDateTimeToDate(plusMin);
    }

    /**
     * 在当前时间加小时
     * @param hour 小时(可为负数)
     * @return 日期
     */
    public static Date plusHourBaseOnCurrentDate(int hour) {
        LocalDateTime currentLocalDateTime = getCurrentLocalDateTime(null);
        LocalDateTime plusHours = currentLocalDateTime.plusHours(hour);
        return convertLocalDateTimeToDate(plusHours);
    }

    /**
     * 获取第二天的凌晨三点 - 主要用于JWT过期时间
     * @return 日期
     */
    public static Date getThreeOclockAMOfTheNextDay() {
        LocalDateTime currentLocalDateTime = getCurrentLocalDateTime(null);
        LocalDateTime nextDay = currentLocalDateTime.plusDays(1);
        LocalDateTime threeOclockAMOfTheNextDay = LocalDateTime.of(nextDay.getYear(), nextDay.getMonth(), nextDay.getDayOfMonth(), 3, 0, 0);
        return convertLocalDateTimeToDate(threeOclockAMOfTheNextDay);
    }

	/**
	 * 去除时间毫秒数中的分时秒
	 *
	 * @return
	 */
	public static Calendar getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Calendar c = Calendar.getInstance();
		c.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);
		return c;
	}

	/**
	 * 获取两日期之间所有日期，返回数组
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    public static List<String> getDateStringBetween(Date startDate, Date endDate) {
		Calendar start = getDate(startDate != null ? startDate : new Date());

		Calendar end = getDate(endDate != null ? endDate : new Date());

		List<String> dates = new ArrayList<String>();

 		while(le(start, end)){
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATTER_EN);
			String str = sdf.format(start.getTime());
			dates.add(str);
			start.add(Calendar.DAY_OF_MONTH, 1);
		}

		return dates;
	}

	/**
	 * 比较年月日
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean le(Calendar c1, Calendar c2){
		Integer start = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DAY_OF_MONTH);
		Integer end = c2.get(Calendar.YEAR) * 10000 + c2.get(Calendar.MONTH) * 100 + c2.get(Calendar.DAY_OF_MONTH);
		return start <= end;
	}
}
