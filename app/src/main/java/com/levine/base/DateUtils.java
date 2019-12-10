package com.levine.base;

import com.levine.utils.base.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/16.
 */

public class DateUtils {

    /**
     * @return 获取当前月的天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取某年某月的天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    public static boolean isToday(int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String currentTime=sdf.format(date);
        int mYear= Integer.valueOf(currentTime.split("-")[0]);
        int mMonth= Integer.valueOf(currentTime.split("-")[1]);
        int mDay= Integer.valueOf(currentTime.split("-")[2]);
        if(mYear==year&&mMonth==month&&mDay==day){
            return true;
        }
        return false;
    }

    public static String getCurrentYearAndMonth() {
        Calendar a = Calendar.getInstance();
        int year = a.get(Calendar.YEAR);
        int month = a.get(Calendar.MONTH) + 1;
        return year + "年" + month + "月";
    }

    /**
     * 获取当前月的第一天是星期几
     * @return
     */
    public static int getWeekDayOfFirstDayInMonth() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DAY_OF_MONTH, 1);
        int i = a.get(Calendar.DAY_OF_WEEK);
        switch (i){
            case 1:return 7;//星期天
            case 2:return 1;//星期一
            case 3:return 2;//星期二
            case 4:return 3;//星期三
            case 5:return 4;//星期四
            case 6:return 5;//星期五
            default:return 6;//星期六
        }
    }

    /**
     * 获取某年某月的第一天是星期几
     * @return
     */
    public static int getWeekDayOfFirstDayInMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR,year);
        a.set(Calendar.MONTH,month-1);
        a.set(Calendar.DAY_OF_MONTH, 1);
        int i = a.get(Calendar.DAY_OF_WEEK);
        switch (i){
            case 1:return 7;//星期天
            case 2:return 1;//星期一
            case 3:return 2;//星期二
            case 4:return 3;//星期三
            case 5:return 4;//星期四
            case 6:return 5;//星期五
            default:return 6;//星期六
        }
    }

    /**
     * 获取某年某月的最后一天是星期几
     * @return
     */
    public static int getWeekDayOfLastDayInMonth(int year, int month) {
        int daysOfMonth=getDaysOfMonth(year,month);
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR,year);
        a.set(Calendar.MONTH,month-1);
        a.set(Calendar.DAY_OF_MONTH,daysOfMonth);

        int i = a.get(Calendar.DAY_OF_WEEK);
        switch (i){
            case 1:return 7;//星期天
            case 2:return 1;//星期一
            case 3:return 2;//星期二
            case 4:return 3;//星期三
            case 5:return 4;//星期四
            case 6:return 5;//星期五
            default:return 6;//星期六
        }
    }


    /**
     * 获取Date对象
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDateByCalendar(int year,int month,int day){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar.getTime();
    }

}