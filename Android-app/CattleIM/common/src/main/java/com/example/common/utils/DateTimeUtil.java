package com.example.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class DateTimeUtil {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);

    public static final SimpleDateFormat FORMAT_TIME_LINE = new SimpleDateFormat("dd:ss", Locale.ENGLISH);

    public static final SimpleDateFormat Detail_TIME_LINE = new SimpleDateFormat("yy:MM:dd:ss", Locale.ENGLISH);


    /**
     * 获取一个简单的时间字符串
     *
     * @param date Date
     * @return 时间字符串
     */
    public static String getSampleDate(Date date) {
        return FORMAT.format(date);
    }

    /**
     *
     * 用来处理 timeline  的时间的显示问题
     */
    public static String getTimeline(Date date) {
        return FORMAT_TIME_LINE.format(date) + getAMOrPM(date);
    }


    /**
     *
     * 用来处理 timeline  中不规则时间的规则化操作
     */
    public static String getFormatTimeline(String s) {
        Date dateTime = getDateTime(s, FORMAT_TIME_LINE);
        return FORMAT_TIME_LINE.format(dateTime) ;
    }


    /**
     *
     * 将固定格式的时间，转化为对象
     */
    public static Date getDateTime(String dateString, SimpleDateFormat format) {
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 用来处理显示当前是上午还是下午
     * @param date  传入一个时间对象
     * @return
     */
    public static String getAMOrPM(Date date){
        GregorianCalendar ca = new GregorianCalendar();
        ca.setGregorianChange(date);
        switch (ca.get(GregorianCalendar.AM_PM)){
            case 0:
                return "am";
            default:
                return "pm";

        }
    }


    /**
     * 获取当前后几分钟
     * @param after  分钟
     */
    public static String getNowTimeAfter(int after, SimpleDateFormat format){
        long currentTime = System.currentTimeMillis() + after * 60 * 1000;
        Date date = new Date(currentTime);
        return format.format(date);
    }

    /**
     * 当前的后几个天
     */
    public static String getNowDateAfter(int afterDate, SimpleDateFormat format){
        long currentTime = System.currentTimeMillis() + afterDate * 60 * 60 * 24 *1000 ;
        Date date = new Date(currentTime);
        return format.format(date);
    }

    /**
     *
     * 用来规范化年月时间
     */
    public static String getFormatTaskDate(String startTimeString) {
        Date dateTime = getDateTime(startTimeString, FORMAT);
        return FORMAT.format(dateTime) ;
    }
}
