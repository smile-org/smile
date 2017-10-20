package com.dli.services.impl;


import com.dli.entities.Demo;
import com.dli.entities.LogonHistory;
import com.dli.entities.SMS;
import com.dli.entities.User;
import com.dli.repositories.LogonRepo;
import com.dli.repositories.LogonRepo;
import com.dli.services.DemoService;
import com.dli.services.LogonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogonServiceImpl implements LogonService {

    @Autowired
    private LogonRepo logonRepo;


    @Override
    public User getUserByPhoneNumber(String cellphone) {

        return logonRepo.getUserByPhoneNumber(cellphone);

    }

    @Override
    public void addSMS(int userid, String cellphone, String vcode, String smstype, Date now) {
        logonRepo.addSMS(userid, cellphone, vcode, smstype, now);
    }

    @Override
    public boolean checkSMS(String cellphone, String vcode) {

        int count = logonRepo.checkSMS(cellphone, vcode);
        if (count > 0)
            return true;
        else
            return false;
    }

    @Override
    public void updatePwdByCellphone(String pwd, String cellphone) {
        logonRepo.updatePwdByCellphone(pwd, cellphone);
    }

    @Override
    public boolean logon(String pwd, String cellphone) {

        int count = logonRepo.logon(pwd, cellphone);
        if (count > 0)
            return true;
        else
            return false;
    }

    @Override
    public User getUserByID(int id) {
        return   logonRepo.getUserByID(id);
    }

    @Override
    public User getUserByToken(String token) {
        return logonRepo.getUserByToken(token);
    }

    @Override
    public void deleteSMSOverdue() {
        logonRepo.deleteSMSOverdue();
    }

    @Override
    public boolean allowGetSMSByCellphone(String cellphone) {

         List<SMS>    smsList=   logonRepo.getSMSHistoryByCellphone(cellphone);

         if( smsList.size()==0 || smsList==null )
             return  true;

         if( smsList.size()!=3 )
             return  true;

        SMS  histroy= smsList.get(smsList.size()-1 );

        if(  (new Date().getTime()-   histroy.getCreated_at().getTime())/1000  >30*60 )
             return   true;
        else
            return  false;

    }

    @Override
    public boolean backlogon(String pwd, String cellphone) {

        int count = logonRepo.backlogon(pwd, cellphone);
        if (count > 0)
            return true;
        else
            return false;


    }

    @Override
    public void addLogonHistory(int userid) {
        logonRepo.addLogonHistory(userid);
    }

    @Override
    public List<LogonHistory> backGetLogonHistoryList(LogonHistory h) {
        return    logonRepo.backGetLogonHistoryList(h);
    }


}
