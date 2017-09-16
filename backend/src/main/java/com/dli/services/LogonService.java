package com.dli.services;


import com.dli.entities.Demo;
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
}
