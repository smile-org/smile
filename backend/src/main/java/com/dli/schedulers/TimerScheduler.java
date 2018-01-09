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
    @Scheduled(initialDelay = 1000, fixedDelay = 5000 * 60)
    public void sendMessage() {
        try {
            userService.sendMessage();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }
}
