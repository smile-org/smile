package com.dli.services.impl;


import com.dli.entities.Demo;
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


}
