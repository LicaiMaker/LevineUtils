package com.levine.base;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDateUtils(){

        int firstDayOfMonth = DateUtils.getWeekDayOfFirstDayInMonth(2019, 12);
        int firstDayOfMonth1 = DateUtils.getWeekDayOfFirstDayInMonth(2019, 11);
        int firstDayOfMonth2 = DateUtils.getWeekDayOfFirstDayInMonth(2019, 10);
        int firstDayOfMonth3 = DateUtils.getWeekDayOfFirstDayInMonth(2019, 9);

////        System.out.println(firstDayOfMonth+","+firstDayOfMonth1+","+firstDayOfMonth2+","+firstDayOfMonth3);
//        int firstDay=DateUtils.getWeekDayOfFirstDayInMonth();
//        System.out.println("firstDay:"+firstDay);
//        DateUtils.isToday(2019,12,10);

        int lastDayOfMonth = DateUtils.getWeekDayOfLastDayInMonth(2019, 11);
        System.out.println(lastDayOfMonth);


    }
}