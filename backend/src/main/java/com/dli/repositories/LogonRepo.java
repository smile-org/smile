package com.dli.repositories;


import com.dli.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface LogonRepo {

    User getUserByPhoneNumber(String cellphone);

    void addSMS(@Param("userid") int userid, @Param("cellphone") String  cellphone,
                @Param("vcode")String vcode, @Param("smstype")String smstype,@Param("now") Date now);

    int checkSMS(@Param("cellphone") String  cellphone, @Param("vcode")String vcode);

    void  updatePwdByCellphone(@Param("pwd") String  pwd,@Param("cellphone") String  cellphone);

    int logon(@Param("pwd") String  pwd,@Param("cellphone") String  cellphone);

    User getUserByID(@Param("id")  int id);

    User getUserByToken(String token);

     void  deleteSMSOverdue();

     List<SMS>  getSMSHistoryByCellphone( @Param("cellphone") String  cellphone);

    int backlogon(@Param("pwd") String  pwd,@Param("cellphone") String  cellphone);

    void  addLogonHistory(int userid);

    List<LogonHistory>  backGetLogonHistoryList(LogonHistory   h);

    int backGetLogonHistoryListCount(LogonHistory   h);



    int adminlogon(@Param("pwd") String  pwd,@Param("cellphone") String  cellphone);

}
