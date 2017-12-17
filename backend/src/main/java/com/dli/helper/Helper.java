package com.dli.helper;

import com.dli.entities.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Helper {


    public static String getCurrentWeekBegin() {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
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


        return preMonday + " 00:00:00";

    }

    public static String getCurrentMonthBegin() {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday;
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());

        return firstday + " 00:00:00";
    }


    // 1. 学课  2.考试 3. 培训报名 4. 约课

    public static String GetItemType(int typeid) {
        String returnvalue = "";
        switch (typeid) {
            case 1:
                returnvalue = Constant.course_item_type;
                break;
            case 2:
                returnvalue = Constant.exam_item_type;
                break;
            case 3:
                returnvalue = Constant.enrollment_item_type;
                break;
            case 4:
                returnvalue = Constant.appointment_item_type;
                break;
            default:
                break;
        }

        return returnvalue;
    }

    public static int getRandNum(int min, int max) {
        int randNum = min + (int) (Math.random() * ((max - min) + 1));
        return randNum;
    }


    public static void SendMessage(String cellphone, String message) {

    }

    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.trim().equals("")) return true;
        else return false;
    }

    public static String formatDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = sdf.format(d);
        return result;
    }

    public static String formatDateWithoutTime(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(d);
        return result;
    }

    public static String getFileNameExtension(String fileName) {
        String[] arr = fileName.split("\\.");
        return arr[arr.length - 1];
    }

    public static Date dateParse(String dateString)
            throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            return date;
        } catch (Exception e) {
            throw e;
        }
    }


    public static String Export(List<String> rowNameList, List<Object[]> dataList, String tempFileName, String fileroot, String exportfolder)
            throws Exception {

        try {


            String path = fileroot + exportfolder;
            File targetFile = new File(path);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            String fullPath = path + tempFileName + UUID.randomUUID() + ".xlsx";
            OfficeUtil.getInstance().export2excel("sheet1", rowNameList, dataList, fullPath);
            return fullPath.replace(fileroot, "");

        } catch (Exception ex) {
            throw ex;
        }

    }


    public static Date addOneDay(String date) throws Exception {
        try {
            Date endDate = dateParse(date);

            Calendar cl = Calendar.getInstance();
            cl.setTime(endDate);
            cl.add(Calendar.DAY_OF_MONTH, 1);// 今



            return  cl.getTime();


        }
        catch (Exception ex) {
            throw ex;
        }
    }


    public  static   String  GenerateToken(  String   phone ) throws Exception{

        String randmom = String.valueOf(Helper.getRandNum(0, 999999));
           String str= phone + randmom;
           MessageDigest md5= MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();

           String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
           return newstr;
    }

    /*
    public  static   void    Export(List<String> rowNameList, List<Object[]> dataList, String tempFileName ,
                                    HttpServletRequest request, HttpServletResponse response)
            throws  Exception
    {

        try {


        String path =fileroot +  exportfolder;
        File targetFile = new File(path);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        String fullPath =   path + tempFileName+ UUID.randomUUID() +".xlsx";
        OfficeUtil.getInstance().export2excel("sheet1", rowNameList, dataList,fullPath);

        //推流
        File downloadFile = new File(fullPath);
        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        InputStream myStream = new FileInputStream(fullPath);
        IOUtils.copy(myStream, response.getOutputStream());
        response.flushBuffer();
        }
        catch (Exception ex){
            throw ex;
        }

    }
*/

    /*
    * todo

    *2. job to disable course , exam, enroll  while over due expiration date
     3. generate token . when : log on , if token is null.
     4. job to create records into table short_message(table created already) ,  for new user which password is null
     5. job to send message ,record from short_message
     6.  员工，  问题    导入的   读取excel部分
       导出问题列表   没导出选项
     7.  job 清理  course_content 中没有course_id的记录 和对应的 文件， 考虑一下当前正在编辑的课程（也是这种情况，但是不能删除），比如删除一周以前创建的
    *
    *
    * 生成密码
    *  job 比较公司的有效期， 快到一个月的时候给联系人发消息。 过有效期 停用公司。 停用的公司应该不能登陆了， check一下
    *
    * */
}
