package com.support.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TimeConversionUtil
 * @Author 吴俊淇
 * @Date 2020/3/16 11:14
 * @Version 1.0
 * @Description: 2020/3/16 11:14 1.使用DateTimeFormatter替换了部分SimpleDateFormat, 因为线程安全问题。
 * 2.后期这个工具类应该需要优化，因为有Instant,这个专门获取时间戳的类
 * 2020/3/16 11:14 将SimpleDateFormat相关的方法全部去除替换
 **/


public class TimeConversionUtil {


    //传入long时间戳 ，转换成格式化的String类型时间

    public static String TimeOfLongToStrMill(long time) {


        //传入的是精确到毫秒

        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));

    }

    public static String TimeOfLongToStrSecond(long time) {

        //传入的是精确到秒
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault()));

    }

    //传入String类型格式化时间，转换成Long类型的时间戳

    public static long TimeOfStrToLongMill(String strTime) {

        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(strTime, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    }

    public static long TimeOfStrToLongSecond(String strTime) {

        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(strTime, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();

    }


    //通过LocalDateTime获取当前格式化时间

    public static String getTimeStrNow() {
        //DateTimeFormatter替换了SimpleDateFormat, 因为线程安全问题。
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    //通过LocalDateTime获取当前格式化时间

    public static String getTimeStrNow1() {
        //DateTimeFormatter替换了SimpleDateFormat, 因为线程安全问题。
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    //获取当前时间戳方法一

    public static long getTimeLongOfClock() {

        return Clock.systemDefaultZone().millis();
    }

    //获取当前时间戳方法二

    public static long getTimeLongOfCalendar() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getTimeInMillis();
    }

    //获取当前时间戳方法三

    public static long getTimeLongOfSystem() {

        return System.currentTimeMillis();
    }

    //获取当前时间戳方法四

    public static long getTimeLongOfDate() {

        Date date = new Date();

        return date.getTime();
    }

    //获取当前时间戳五 精确到毫秒
    public static long getTimeLongOfInstant() {

        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(0)); //一般传0，如果相差8时区就传8
        //   System.out.println("秒数:"+now.getEpochSecond());
        //   System.out.println("毫秒数:"+now.toEpochMilli());
        return now.toEpochMilli();
    }

    //获取当前时间戳六 精确到秒
    public static long getTimeLongOfInstantEpochSecond() {

        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(0));//一般传0，如果相差8时区就传8
        //   System.out.println("秒数:"+now.getEpochSecond());
        //   System.out.println("毫秒数:"+now.toEpochMilli());
        long longTimeNow = now.getEpochSecond();
        return longTimeNow;
    }

    //获取当前格式化时间 精确到秒
    public static String getTimeStrNowByInstant() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        return dateFormat.format(Instant.now());
    }


    ////工具类调试
    public static void main(String[] args) {
//        TimeConversionUtil timeConversionUtil=new TimeConversionUtil();
//        System.out.println(timeConversionUtil.getTimeLongOfCalendar());
//        System.out.println(timeConversionUtil.getTimeLongOfDate());
//        System.out.println(timeConversionUtil.getTimeLongOfInstant());
//        System.out.println(timeConversionUtil.TimeOfLongToStr(timeConversionUtil.getTimeLongOfDate()));
        //  System.out.println(TimeConversionUtil.getTimeLongOfInstantEpochSecond());
//
//        String creatTime="2017-02-25 08:25:43";
//        long runTimeStamp=30*24*60*60;
//        long l = TimeConversionUtil.TimeOfStrToLongSecond(creatTime);
//        long newTimeStamp=l-runTimeStamp;
//        System.out.println(newTimeStamp);
//        String s = TimeOfLongToStrSecond(newTimeStamp);
//        System.out.println(s);


        System.out.println(getTimeStrNow());

    }

}
