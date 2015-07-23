package com.czh.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by Chenkh on 2015/6/9.
 */
public class DateUtil {
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyyMMdd");
    private final static DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    private final static DateTimeFormatter MOUNTH_FORMAT = DateTimeFormat.forPattern("yyyyMM");

    public static Date parse(String dateStr) {
        return MOUNTH_FORMAT.parseDateTime(dateStr).toDate();
    }

    public static String formatDate(Date date, String pattern) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.print(new DateTime(date.getTime()));
    }

    // 获取当前时间
    public static String getNowTime() {
        return TIME_FORMAT.print(DateTime.now());
    }

    public static String getDateYYYYMMddHHMMSS() {
        return TIME_FORMAT.print(new DateTime());
    }

    public static String getHHMMSS() {
        String time = TIME_FORMAT.print(new DateTime());
        return time.substring(8, 14);
    }

    public static String getDateYYYYMMdd() {
        return DATE_FORMAT.print(new DateTime());
    }


    //获取当前月份
    public static int getCurrentMonth() {
        return DateTime.now().getMonthOfYear();
    }


    //获取上个月份
    public static int getPreviousMonth() {
        return DateTime.now().minusMonths(1).getMonthOfYear();
        //return new DateTime(2014, 1, 20, 13, 14, 0, 0).plusMonths(-1).getMonthOfYear();
    }

    public static String getYYYYMM() {
        return DateTimeFormat.forPattern("yyyyMM").print(new DateTime());
    }


}
