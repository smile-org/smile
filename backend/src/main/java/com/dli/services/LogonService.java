package com.dli.services;


import com.dli.entities.Demo;
import com.dli.entities.LogonHistory;
import com.dli.entities.User;

import java.util.Date;
import java.util.List;

public interface LogonService {

    User getUserByPhoneNumber(String cellphone);

    void addSMS(int userid, String  cellphone, String vcode, String smstype, Date now);

    boolean checkSMS(String  cellphone, String vcode);

    void  updatePwdByCellphone( String  pwd, String  cellphone);

    boolean logon( String  pwd, String  cellphone);

    User getUserByID(int id);

    User getUserByToken(String token);

    void  deleteSMSOverdue();

     boolean     allowGetSMSByCellphone  (  String  cellphone);

    boolean backlogon(String  pwd, String  cellphone);

    void  addLogonHistory(int userid);

    List<LogonHistory>  backGetLogonHistoryList(LogonHistory   h);

    int backGetLogonHistoryListCount(LogonHistory   h);

    boolean adminlogon(String  pwd, String  cellphone);
}
