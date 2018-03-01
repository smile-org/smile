package com.dli.helper;

import com.dli.entities.User;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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


            return cl.getTime();


        } catch (Exception ex) {
            throw ex;
        }
    }


    public static String GenerateToken(String phone) throws Exception {


        String randmom = String.valueOf(Helper.getRandNum(0, 999999));
        String str = phone + randmom;


        String newstr = getEncryption(str);
        return newstr;
/*
        String randmom = String.valueOf(Helper.getRandNum(0, 999999));
           String str= phone + randmom;
           MessageDigest md5= MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();

           String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
           return newstr;

           */
    }


    public static String[] generatePwd() throws Exception {

        String[] arr = new String[2];
        String randmom = String.valueOf(Helper.getRandNum(0, 999999));
        arr[0] = randmom;

        String newstr = getEncryption(randmom);
        arr[1] = newstr;

        return arr;
    }

/*
    public static String getEncryption(String originString)
            throws Exception {
        String result = "";
        if (originString != null) {
            try {
                // 指定加密的方式为MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 进行加密运算
                byte bytes[] = md.digest(originString.getBytes("ISO8859-1"));
                for (int i = 0; i < bytes.length; i++) {
                    // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
                    String str = Integer.toHexString(bytes[i] & 0xFF);
                    if (str.length() == 1) {
                        str += "F";
                    }
                    result += str;
                }
            } catch (Exception e) {
                throw  e;
            }
        }
        return result;
    }
*/


    private static String getEncryption(String sourceStr) throws Exception {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            //System.out.println("MD5(" + sourceStr + ",32) = " + result);
            // System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (Exception e) {
            //System.out.println(e);
            throw e;
        }
        return result;

    }



    /**
     * 生成当前UTC时间戳
     *
     * @return
     */

    private final static String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static String generateUTCTimestamp() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static String generateRandom() {
        String signatureNonce = UUID.randomUUID().toString();
        return signatureNonce;
    }


    /*
    todo






    *
    *
    * */
}



