package com.tzj.frame.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：时间转化工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2015-12-17 11:17
 */
public class TimeUtil {

    /**
     * 时间戳转化为yyyy-MM-dd的格式
     *
     * @param stamp 时间戳
     * @return
     */
    public static String stampToYyyyMMdd(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(new Date(stamp * 1000L));
        return result;
    }

    /**
     * 时间戳转化为yyyy-MM-dd HH:mm:ss的格式
     *
     * @param stamp 时间戳
     * @return
     */
    public static String stampToYyyyMMddHHmmss(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = sdf.format(new Date(stamp * 1000L));
        return result;
    }

    /**
     * yyyy-MM-dd格式时间转化为时间戳
     *
     * @param date
     * @return
     */
    public static String yyyyMMddToStamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStamp = "";
        try {
            Date mDate = sdf.parse(date);
            dateStamp = mDate.getTime() / 1000L + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStamp;
    }

    /**
     * 获得系统当前日期yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDateYyyyMMdd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeStamp() {
        return (new Date()).getTime();
    }

    /**
     * 获得系统当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTimeYyyyMMddHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得系统当前时间HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTimeHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

}
