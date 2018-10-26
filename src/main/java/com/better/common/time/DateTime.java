package com.better.common.time;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 *
 * @author 陈平
 * @version 1.0
 * @since 14-8-1
 */
@SuppressWarnings("unused")
public class DateTime {

    private static final Logger logger = LoggerFactory.getLogger(DateTime.class);

    private static final Object LOCK_OBJ = new Object();
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final String YMD = "yyyyMMdd";
    private static final String YMDHMS = "yyyyMMddHHmmss";
    private static final String YMDHM = "yyyyMMddHHmm";
    private static final String Y_M_D = "yyyy-MM-dd";
    private static final String YMD_H_M_S = "yyyyMMdd HH:mm:ss";
    private static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    private DateTime() {
        throw new IllegalAccessError("Utility class");
    }

    private final static String[] DAY_NAMES = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
            "星期六"};

    /**
     * 周一
     *
     * @return Date
     */
    public static String getWeekStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getDateStr8(calendar.getTime());
    }

    /**
     * 周日
     *
     * @return Date
     */
    public static String getWeekEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, 6);
        Date date = calendar.getTime();
        return getDateStr8(date);
    }

    public static List<String> getWeekDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YMD);
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            Date date = calendar.getTime();
            list.add(getDateStr8(date));
            calendar.add(Calendar.DATE, 1);
        }
        return list;
    }

    /**
     * 1号
     *
     * @return Date
     */
    public static Date getMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 月末
     *
     * @return Date
     */
    public static Date getMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 月初
     *
     * @param m 月
     * @return Date
     */
    public static Date getMonthStart(int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, m);
        return calendar.getTime();
    }

    /**
     * 月末
     *
     * @param m 月
     * @return Date
     */
    public static Date getMonthEnd(int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 季度初
     *
     * @return Date
     */
    public static Date getQuarterStart() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (month == Calendar.JANUARY || month == Calendar.FEBRUARY || month == Calendar.MARCH) {
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else if (month == Calendar.APRIL || month == Calendar.MAY || month == Calendar.JUNE) {
            calendar.set(Calendar.MONTH, Calendar.APRIL);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else if (month == Calendar.JULY || month == Calendar.AUGUST || month == Calendar.SEPTEMBER) {
            calendar.set(Calendar.MONTH, Calendar.JULY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else if (month == Calendar.OCTOBER || month == Calendar.NOVEMBER || month == Calendar.DECEMBER) {
            calendar.set(Calendar.MONTH, Calendar.OCTOBER);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.getTime();
    }

    /**
     * 季度末
     *
     * @return Date
     */
    public static Date getQuarterEnd() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (month == Calendar.JANUARY || month == Calendar.FEBRUARY || month == Calendar.MARCH) {
            calendar.set(Calendar.MONTH, Calendar.APRIL);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
        } else if (month == Calendar.APRIL || month == Calendar.MAY || month == Calendar.JUNE) {
            calendar.set(Calendar.MONTH, Calendar.JULY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
        } else if (month == Calendar.JULY || month == Calendar.AUGUST || month == Calendar.SEPTEMBER) {
            calendar.set(Calendar.MONTH, Calendar.OCTOBER);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
        } else if (month == Calendar.OCTOBER || month == Calendar.NOVEMBER || month == Calendar.DECEMBER) {
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    /**
     * 年初
     *
     * @return Date
     */
    public static Date getYearStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 年末
     *
     * @return Date
     */
    public static Date getYearEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 开始日期
     *
     * @param date 日期
     * @return Date 时间为00:00:00
     */
    public static Date getBdate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 结束日期
     *
     * @param date 日期
     * @return Date 时间为：23:59:59
     */
    public static Date getEdate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 开始日期
     *
     * @param date yyyy-MM-dd 日期
     * @return Date 时间为00:00:00
     */
    public static Date getBdate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            logger.error("", e);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 结束日期
     *
     * @param date yyyy-MM-dd 日期
     * @return Date 时间为：23:59:59
     */
    public static Date getEdate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            logger.error("", e);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 格式日期yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return String
     */
    public static String getBdateStr(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D_H_M_S);
            return sdf.format(getBdate(date));
        } else {
            return "";
        }
    }

    /**
     * 格式日期yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return String
     */
    public static String getEdateStr(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D_H_M_S);
            return sdf.format(getEdate(date));
        } else {
            return "";
        }
    }

    public static String getDateStr8() {
        return getDateStr8(new Date());
    }

    /**
     * 格式日期yyyyMMdd
     *
     * @param date 日期
     * @return String
     */
    public static String getDateStr8(Date date) {
        if (date != null) {
            SimpleDateFormat sdf8 = new SimpleDateFormat(YMD);
            return sdf8.format(date);
        } else {
            return "";
        }
    }

    public static String getDateStr14() {
        Date date = new Date();
        return getDateStr14(date);
    }

    /**
     * 格式日期yyyyMMdd
     *
     * @param date 日期
     * @return String
     */
    public static String getDateStr14(Date date) {
        if (date != null) {
            SimpleDateFormat sdf14 = new SimpleDateFormat(YMDHMS);
            return sdf14.format(date);
        } else {
            return "";
        }
    }

    public static String getDateStr19() {
        Date date = new Date();
        return getDateStr19(date);
    }

    /**
     * 格式日期yyyyMMdd
     *
     * @param date 日期
     * @return String
     */
    public static String getDateStr19(Date date) {
        if (date != null) {
            return getSdf19().format(date);
        } else {
            return "";
        }
    }

    /**
     * 格式日期yyyy-MM-dd
     *
     * @param date 日期
     * @return String
     */
    public static String getDateStr10(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 格式日期yyyy-MM-dd HH:mm
     *
     * @param date 日期
     * @return String
     */
    public static String getTimeMinStr(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 格式日期yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return String
     */
    public static String getTimeStr(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(Y_M_D_H_M_S);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 格式日期yyyyMMdd HH:mm:ss
     *
     * @param date 日期
     * @return String
     */
    public static String getTimeStr17(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 格式时间HHmmss
     *
     * @param date 日期
     * @return String
     */
    public static String getTime6(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 格式时间HHmmss
     *
     * @param
     * @return String
     */
    public static String getTime6() {
        return getTime6(new Date());
    }

    /**
     * 格式日期yyyyMMdd
     *
     * @param date 日期
     * @return int
     */
    public static int getIntDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YMD);
        return Integer.parseInt(sdf.format(date));
    }

    /**
     * 格式日期yyyyMMddHHmmss
     *
     * @param date 日期
     * @return long
     */
    public static long getLongTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
        return Long.parseLong(sdf.format(date));
    }

    /**
     * 格式日期yyyyMMddHHmmss
     *
     * @param date 日期
     * @return 14位日期
     */
    public static String getTime14(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
        return sdf.format(date);
    }

    /**
     * yyyyMMdd转yyyy-MM-dd
     *
     * @param date yyyyMMdd日期
     * @return yyyy-MM-dd日期
     */
    public static String date8to10(String date) {
        SimpleDateFormat sdf8 = new SimpleDateFormat(YMD);
        SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf10.format(sdf8.parse(date));
        } catch (ParseException e) {
            logger.error("", e);
            return date;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转 yyyyMMdd
     *
     * @param date yyyy-MM-dd HH:mm:ss日期
     * @return yyyyMMdd
     */
    public static String date8Str(String date) {
        SimpleDateFormat sdf8 = new SimpleDateFormat(YMD);
        return sdf8.format(date);
    }

    public static String date8toFin(String date) {
        SimpleDateFormat sdf8 = new SimpleDateFormat(YMD);
        SimpleDateFormat sdfFin = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return sdfFin.format(sdf8.parse(date));
        } catch (ParseException e) {
            logger.error("", e);
            return date;
        }
    }

    /**
     * yyyy-MM-dd转yyyyMMdd
     *
     * @param date yyyy-MM-dd日期
     * @return yyyyMMdd日期
     */
    public static String date10to8(String date) {
        SimpleDateFormat sdf8 = new SimpleDateFormat(YMD);
        SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf8.format(sdf10.parse(date));
        } catch (ParseException e) {
            logger.error("", e);
            return date;
        }
    }

    public static Date date8toDate(String date) {
        SimpleDateFormat sdf8 = new SimpleDateFormat(YMD);
        try {
            return sdf8.parse(date);
        } catch (ParseException e) {
            logger.error("", e);
            return new Date();
        }
    }

    public static Date date10toDate(String date) {
        SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf10.parse(date);
        } catch (ParseException e) {
            logger.error("", e);
            return new Date();
        }
    }

    public static Date timetoDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            logger.error("", e);
            return new Date();
        }
    }

    public static Date date14toDate(String date) {
        SimpleDateFormat sdf14 = new SimpleDateFormat(YMDHMS);
        try {
            return sdf14.parse(date);
        } catch (ParseException e) {
            logger.error("", e);
            return new Date();
        }
    }

    public static Date strtoDate(String date, String pattern) {
        SimpleDateFormat sdf10 = new SimpleDateFormat(pattern);
        try {
            return sdf10.parse(date);
        } catch (ParseException e) {
            logger.error("", e);
            return new Date();
        }
    }

    public static Date getMonthBefore(int count) {
        return getMonthDiffer(new Date(), Math.abs(count) * -1);
    }

    public static Date getMonthBefore(Date date, int count) {
        return getMonthDiffer(date, Math.abs(count) * -1);
    }

    public static Date getMonthDiffer(Date date, int count) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, count);
        return cal.getTime();
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String getDateDayDiff(String day, int diff) {
        assert day != null;
        return getDateDiff(day.length() < YMD.length() ? StringUtils.rightPad(day, YMD.length(), "0") : day.substring(0, YMD.length()), YMD, Calendar.DAY_OF_YEAR, diff);
    }

    public static String getDateDayDiff(String day, String format, int diff) {
        return getDateDiff(day, format, Calendar.DAY_OF_YEAR, diff);
    }

    public static String getDateMonthDiff(String day, Integer diff) {
        return getDateDiff(day, YMD, Calendar.MONTH, diff);
    }

    public static String getDateMonthDiff(String day, String format, Integer diff) {
        return getDateDiff(day, format, Calendar.MONTH, diff);
    }

    public static String getDateDiff(String day, String format, int field, int diff) {
        Date date;
        SimpleDateFormat sdf = getSdf(format);
        try {
            date = sdf.parse(day);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(field, diff);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            logger.error("", e);
        }
        return day;
    }

    public static Long formatLongFromStr(String dateFormat) {
        assert dateFormat != null;
        try {
            SimpleDateFormat sdf14 = getSdf14();
            return sdf14.parse(dateFormat.length() < YMDHMS.length() ? StringUtils.rightPad(dateFormat, YMDHMS.length(), "0") : dateFormat.substring(0, YMDHMS.length())).getTime();
        } catch (ParseException e) {
            logger.error("", e);
        }
        return System.currentTimeMillis();
    }

    public static SimpleDateFormat getSdf19() {
        return getSdf(Y_M_D_H_M_S);
    }

    public static SimpleDateFormat getSdf14() {
        return getSdf(YMDHMS);
    }

    public static SimpleDateFormat getSdf12() {
        return getSdf(YMDHM);
    }

    public static SimpleDateFormat getSdf17() {
        return getSdf(YMD_H_M_S);
    }

    public static SimpleDateFormat getSdf8() {
        return getSdf(YMD);
    }

    public static SimpleDateFormat getSdf10() {
        return getSdf(Y_M_D);
    }

    public static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        if (tl == null) {
            synchronized (LOCK_OBJ) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    System.out.println("put new sdf of pattern " + pattern + " to map");
                    tl = new ThreadLocal<SimpleDateFormat>() {

                        @Override
                        protected SimpleDateFormat initialValue() {
                            System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 根据传入的8位日期，获取下一天的8位日期
     *
     * @param today 当前日期的8位日期格式
     * @return 自然日的下一天的8位日期格式。
     */
    public static String getNextDay(String today) {
        SimpleDateFormat sdf = new SimpleDateFormat(YMD);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(today));
        } catch (ParseException e) {
            logger.error("context", e);
        }
        cal.add(Calendar.DATE, 1);
        return sdf.format(cal.getTime());
    }

    /**
     * 根据传入的14位日期，获取下一天的14位日期
     *
     * @param format14 当前日期的14位日期格式
     * @return 自然日的下一天的14位日期格式。
     */
    public static String getNextDayFormat14(String format14) {
        SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(format14));
        } catch (ParseException e) {
            logger.error("context", e);
        }
        cal.add(Calendar.DATE, 1);
        return sdf.format(cal.getTime());
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static String[] getWeekBegEnd() {
        Calendar calendar = Calendar.getInstance();
        String begDate,
                endDate;
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        begDate = DateTime.getDateStr8(calendar.getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        endDate = DateTime.getDateStr8(calendar.getTime());
        return new String[]{begDate, endDate};
    }

    public static String getMonthFirst(String dateStr8) {
        Date date = null;
        try {
            date = DateTime.getSdf8().parse(dateStr8);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateTime.getDateStr8(calendar.getTime());
    }

    public static String getPrevMonthStr() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, -1);
        String dateStr8 = DateTime.getDateStr8(instance.getTime());
        return dateStr8;
    }

    public static String getPrevMonthLastDayStr() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.DAY_OF_MONTH, 1);
        instance.add(Calendar.DAY_OF_YEAR, -1);
        String dateStr8 = DateTime.getDateStr8(instance.getTime());
        return dateStr8;
    }

    public static String getThisMonthLastDayStr() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, 1);
        instance.set(Calendar.DAY_OF_MONTH, 1);
        instance.add(Calendar.DAY_OF_YEAR, -1);
        String dateStr8 = DateTime.getDateStr8(instance.getTime());
        return dateStr8;
    }

    public static String getPrevDayStr() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_YEAR, -1);
        String dateStr8 = DateTime.getDateStr8(instance.getTime());
        return dateStr8;
    }
}
