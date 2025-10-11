package com.linearity.utils.AndroidFakes.Alarm;


import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AlarmTimestampGenerator {
    /**
     * Uses to generate timestamp for alarm,it should be like alarm for working class.
     * <p>Destroy imperialists and revisionists!</p>
     */
    public static final int[][] HOUR_AND_MINUTE_FOR_A_DAY_WORKING_CLASS_PRESENT = {
            {7,15},//morning,go to work
            {12,0},//go to take lunch
            {13,50},//sleep in the noon
            {18,0}//go home
    };
    /**
     * from MONDAY to Sunday
     * Mon Tue Wed Thur Fri Sat Sun
     * <p>(hint:Working class in China have little chance to rest in Sundays.</p>
     * <p>and they also have little chance to rest on holidays.)</p>
     */
    public static final boolean[] WORK_DAYS_IN_A_WEEK_WORKING_CLASS_PRESENT = {
            true,true,true,true,true,true,true,
    };

    //TODO:Generate correct timestamp
    public static long getNextAlarmTimestamp(int[][] hourAndMinutePresent,boolean[] workDaysInAWeekPresent){
        int workDaysCounter = 0;
        for (boolean flag:workDaysInAWeekPresent){
            if (flag){workDaysCounter+=1;}
        }
        if (workDaysCounter == 0){
            return 0;//when you don't need to work
        }
        Date date = new Date(System.currentTimeMillis());
        Calendar instance = Calendar.getInstance();
        instance.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC+8")));
        return 0L;
    }

}
