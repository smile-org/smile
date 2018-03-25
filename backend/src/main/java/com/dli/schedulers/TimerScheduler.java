package com.dli.schedulers;


import com.dli.entities.adminCompany;
import com.dli.services.CompanyService;
import com.dli.services.ExamService;
import com.dli.services.LogonService;
import com.dli.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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


    @Value("${sqlhost}")
    private String sqlhost;

    @Value("${sqluser}")
    private String sqluser;

    @Value("${sqlpwd}")
    private String sqlpwd;

    @Value("${sqldb}")
    private String sqldb;




    @Value("${defaultheader}")
    private String defaultheader;

    @Value("${defaulthomelogo}")
    private String defaulthomelogo;

    @Value("${defaulthomebanner}")
    private String defaulthomebanner;

    //第一次延迟1秒执行，当执行完后5分钟再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 10000 * 10)

    //每天晚上5点执行
    //@Scheduled(cron = "00 00 05 * * ?")
    public void syncCompany() {

        Connection conn = null;
            try {

                String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                String dbURL = "jdbc:sqlserver://"+sqlhost+":1433;DatabaseName="  +sqldb;
                Class.forName(driverName);
                conn = DriverManager.getConnection(dbURL, sqluser, sqlpwd);

                String sql = "select * from tb_user";
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery(sql);//定义ResultSet类，用于接收获取的数据
                while (rs.next())
                {
                    String phone_number = rs.getString("tel");
                    int count =  userService.getUserByCellphone(phone_number);
                    if( count >0 )     continue;


                    String company_name = rs.getString("company");
                    String contact_person = rs.getString("contact");
                    int user_limit = 100;

                    Calendar calendar = Calendar.getInstance();
                    Date expiration_date = new Date();
                    calendar.setTime(expiration_date);
                    calendar.add(Calendar.YEAR, 1);
                    expiration_date = calendar.getTime();

                    adminCompany c = new adminCompany();
                    c.setCompany_name(company_name);
                    c.setContact_person(contact_person);
                    c.setPhone_number(phone_number);
                    c.setUser_limit(user_limit);
                    c.setExpiration_date(expiration_date);
                    companyService.adminAddCompany(  defaultheader,c        ,true);

                    //添加 logo和banner
                    c.setPic_url(defaulthomebanner);
                    c.setPic_type("banner");
                    companyService.adminAddCompanyPic(c);

                    c.setPic_url(defaulthomelogo);
                    c.setPic_type("logo");
                    companyService.adminAddCompanyPic(c);
                }
                rs.close();
                stat.close();
            }
            catch (Exception ex)
            {
                logger.error(ex.getMessage());
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
                    logger.error(ex.getMessage());
                }
            }


    }
}
