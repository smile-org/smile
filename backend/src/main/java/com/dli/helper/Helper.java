package com.dli.helper;

import com.dli.entities.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Helper {



    public static String getCurrentWeekBegin() {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if(dayOfWeek ==0)
        {
            dayOfWeek=7;
        }

        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();


        //DateFormat df = DateFormat.getDateInstance();
        //String preMonday = df.format(monday);
        //return preMonday + " 00:00:00";


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String preMonday = sdf.format(monday);


        return  preMonday + " 00:00:00";

    }

    public  static  String getCurrentMonthBegin() {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday;
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());

        return   firstday + " 00:00:00";
    }


    // 1. 学课  2.考试 3. 培训报名 4. 约课

    public  static  String GetItemType(int typeid )
    {
        String returnvalue="";
        switch (typeid)
        {
            case  1:returnvalue= Constant.course_item_type; break;
            case  2:returnvalue= Constant.exam_item_type; break;
            case  3:returnvalue= Constant.enrollment_item_type; break;
            case  4:returnvalue= Constant.appointment_item_type; break;
            default:break;
        }

       return  returnvalue;
    }


    public static int getRandNum(int min, int max) {
        int randNum = min + (int)(Math.random() * ((max - min) + 1));
        return randNum;
    }


    public  static void SendMessage(String  cellphone,  String message)
    {

    }


    public  static  String GenerateToken()
    {
        return "123123";
    }

    /*
    * todo

    *2. job to disable course , exam, enroll  while over due expiration date

    *
    * */
}
