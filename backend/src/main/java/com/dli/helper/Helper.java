package com.dli.helper;

import com.dli.entities.User;

public class Helper {

    public   static User GetCurrentUser(){

        User u =new User();
        u.setUser_id(1);
        u.setCompany_id(1);
        u.setFull_name("fullname");
        u.setCell_phone("cellphone");
        u.setPassword("123");

        return   u;

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

   // public  static  int GetCurrentUserID()
    //{
    //    return  1;
   // }

    public static int getRandNum(int min, int max) {
        int randNum = min + (int)(Math.random() * ((max - min) + 1));
        return randNum;
    }


    public  static void SendMessage(String  cellphone,  String message)
    {

    }


    public  static  String GenerateToken()
    {
        return "";
    }

    /*
    * todo
    * 1. job to delete sms that create 5minitues ago , perhaps hourly
    *2. job to disable course while over due expiration date
    *
    * */
}
