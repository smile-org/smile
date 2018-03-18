package com.dli.schedulers;


import com.dli.services.CompanyService;
import com.dli.services.ExamService;
import com.dli.services.LogonService;
import com.dli.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@Component
public class TimerScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    private ExamService examService;


    @Autowired
    private LogonService logonService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    /*//每3秒执行一次
    @Scheduled(fixedRate = 3000)
    public void timerRate() {
        logger.info(sdf.format(new Date()));
    }

    //第一次延迟1秒执行，当执行完后3秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    public void timerInit() {
        logger.info("init : "+sdf.format(new Date()));
    }

    //每天23点27分50秒时执行
    @Scheduled(cron = "50 27 23 * * ?")
    public void timerCron() {
        logger.info("current time : "+ sdf.format(new Date()));
    }*/


    //第一次延迟1秒执行，当执行完后3分钟再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 3000 * 60)
    public void finishhistoryForAllPassTimeLimit() {
        try {
            examService.finishhistoryForAllPassTimeLimit();
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }

    }


    //每天晚上2点执行
   @Scheduled(cron = "00 00 02 * * ?")
    public void deleteSMSOverdue() {
        try {


            logonService.deleteSMSOverdue();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }



    //每天晚上3点执行
     @Scheduled(cron = "00 00 03 * * ?")
    public void comany_expiration_notify() {
        try {


            companyService.notifycompnayNearExpiration();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }


    //每天晚上4点执行
     @Scheduled(cron = "00 00 04 * * ?")
    public void disableCompany() {
        try {


            companyService.jobDisableCompany();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }


    //第一次延迟1秒执行，当执行完后5分钟再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 10)
    public void sendMessage() {
        try {
            userService.sendMessage();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }



    //第一次延迟1秒执行，当执行完后5分钟再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 10000 * 10)
    public void syncCompany() {
        try {


            Connection conn = null;
           // ArrayList students = new ArrayList();//定义与初始化ArrayList数组，相当于定义数组，但是容量比数组大
           // StringBuffer str= new StringBuffer();
            try {
                //获取连接
                String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动

                String dbURL = "jdbc:sqlserver://bds257367494.my3w.com:1433;DatabaseName=bds257367494_db";  //连接服务器和数据库sample
                //运行SQL语句
                String userName = "bds257367494";  //默认用户名
                String userPwd = "123456abc";
                //Class.forName(driverName);
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(dbURL, userName, userPwd);

                /*
                if(conn!=null)
                {
                    System.out.println("Connection Successful!");  //如果连接成功 控制台输出
                }
                else{

                    System.out.println("Connection fail!");
                    return students;
                }
                */


                String sql = "select * from tb_user";//SQL语句，选择数据表student中的所有数据
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(sql);//定义ResultSet类，用于接收获取的数据
                while (rs.next())
                {
                    //实例化VO
                  //  Student student=new Student();
                  //  student.setName(rs.getString("姓名"));
                   // student.setBanji(rs.getString("班级"));
                 //   student.setSex(rs.getString("性别"));
                 //   student.setTime(rs.getString("年龄"));
                //    students.add(student);
                }
                rs.close();
                stat.close();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
            finally
            {
                try
                {//关闭连接
                    if(conn!=null)
                    {
                        conn.close();
                        conn=null;
                    }
                }
                catch(Exception ex)
                {
                }
               // return students;
            }

            userService.sendMessage();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }
}
