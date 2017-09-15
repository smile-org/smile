package com.dli.helper;

public class Helper {

    public  static  int GetCurrentUserID()
    {
        return  1;
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
        return "";
    }

    /*
    * todo
    * 1. job to delete sms that create 5minitues ago , perhaps hourly
    *
    *
    * */
}
