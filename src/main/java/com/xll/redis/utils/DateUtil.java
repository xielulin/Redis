package com.xll.redis.utils;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public final static class DateFormat {
        public static String DF_YYYY_MM_DD_HH_MM = "yyyy年MM月dd日HH时mm分";
    }

    public final static long getTimeStamp(Date d, int offset) {
        final Date date = DateUtils.addDays(d, offset);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
       /* c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);*/
        return c.getTimeInMillis();
    }


    public final static long getTimeStamp(int offset) {
        return getTimeStamp(new Date(), offset);
    }


    public final static long getStateTimeStampOfDay(Long timestamp) {

        return getTimeStamp(new Date(timestamp), 0);
    }

    public final static long getEndTimeStampOfDay(Long timestamp) {
        return getTimeStamp(new Date(timestamp), 1);
    }


    public final static String getText(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getStateTimeStampOfDay(1522368000000L));
        System.out.println(getEndTimeStampOfDay((1522368000000L)));
        System.out.println(getText(DateFormat.DF_YYYY_MM_DD_HH_MM));
        System.out.println(getTimeStamp(-1));
        String _32 = " ";
        String _160 = " ";
        System.out.println(" ".hashCode());
        System.out.println(StringEscapeUtils.unescapeXml("Network&#160;connection&#160;error&#160;please&#160;refresh&#160;and&#160;try&#160;again"));
        System.out.println(StringEscapeUtils.escapeXml("Network connection error please refresh and try again"));
        System.out.println(StringEscapeUtils.escapeXml("Network connection error"));
        System.out.println(StringEscapeUtils.escapeXml("Network connection erro r".replaceAll(_32, _160)));
        System.out.println(StringEscapeUtils.escapeXml("Network connection error".replaceAll(" ", " ")));
    }
}
