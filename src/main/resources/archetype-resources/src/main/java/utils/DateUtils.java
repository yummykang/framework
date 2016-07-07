package ${groupId}.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 日期处理工具类.
 *
 * @author demon
 * @Date 2016/4/21 11:32
 */
public class DateUtils {
    public static final String DATE_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN_2 = "yyyy-MM-dd HH:mm";

    public static final String DATE_PATTERN_3 = "yyyy-MM-dd";

    /**
     * 根据日期格式获取Date
     *
     * @param date 日期参数
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date resultDate = null;
        try {
            resultDate = simpleDateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    /**
     * 获取今天+‘00：00：00’的时间
     *
     * @return
     */
    public static Date getDayStartTime() {
        Calendar todayStart = new GregorianCalendar();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取今天+‘23：59：59’的时间
     *
     * @return
     */
    public static Date getDayEndTime() {
        Calendar todayEnd = new GregorianCalendar();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 给定日期与当前日期相差的天数
     *
     * @param date
     * @return
     */
    public static int dayDiffFromToday(Date date) {
        DateTime now = DateTime.now();
        DateTime param = new DateTime(date);
        return Days.daysBetween(param, now).getDays();
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(getDayBeforeNowEnd(1));
    }

    /**
     * 获取列表查询的start time
     *
     * @param start
     * @return
     */
    public static Date getStart(String start) {
        Date startDate = null;
        if (!StringUtils.isEmpty(start)) {
            startDate = DateUtils.parse(start + " 00:00:00", DateUtils.DATE_PATTERN_1);
        }
        return startDate;
    }

    /**
     * 获取列表查询的end time
     *
     * @param end
     * @return
     */
    public static Date getEnd(String end) {
        Date endDate = null;
        if (!StringUtils.isEmpty(end)) {
            endDate = DateUtils.parse(end + " 23:59:59", DateUtils.DATE_PATTERN_1);
        }
        return endDate;
    }

    /**
     * 获取今天之前的日期
     *
     *
     * @param minus
     * @return
     */
    public static Date getDayBeforeNow(int minus) {
        DateTime result = new DateTime().minusDays(minus);
        return result.toLocalDate().toDate();
    }

    /**
     * 获取今天之前的日期 + “00：00：00”
     *
     * @param minus
     * @return
     */
    public static Date getDayBeforeNowStart(int minus) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getDayBeforeNow(minus));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取今天之前的日期+“23：59：59.999”
     *
     * @param minus
     * @return
     */
    public static Date getDayBeforeNowEnd(int minus) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getDayBeforeNow(minus));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取前n日的日期(string)
     *
     * @param n 几天前
     * @return
     */
    public static List<String> getLastDateStrList(int n) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_3);
        List<String> dateList = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            dateList.add(simpleDateFormat.format(getDayBeforeNow(i)));
        }
        Collections.sort(dateList);
        return dateList;
    }

    /**
     * 获取前n日的日期(date)
     *
     * @param n
     * @return
     */
    public static List<Date> getLastDateList(int n) {
        List<Date> dateList = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            dateList.add(getDayBeforeNow(i));
        }
        return dateList;
    }

    /**
     * date转成string
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
