package com.dli.controllers;


import com.dli.entities.LogonHistory;
import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.CompanyService;
import com.dli.services.LogonService;
import com.dli.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
public class LogonController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired

    private LogonService logonService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.GET)
    public Map getVerificationCode(String cellphone) {

        /*  check phone number
        generate vcode and store in database
        *   send message
        *
        * */

        Map<String, Object> result = new HashMap<String, Object>();
        try {


            User u = logonService.getUserByPhoneNumber(cellphone);

            if (u == null) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "手机号码不存在");
            } else {
               boolean allow=  logonService.allowGetSMSByCellphone(cellphone);

                if(!allow){

                    result.put(Constant.status, 0);
                    result.put(Constant.result, "您获取验证码过于频繁，请过30分钟再尝试");

                }
                    else {
                    String vcode = String.valueOf(Helper.getRandNum(0, 999999));
                    //Helper.SendMessage(cellphone, "[smile]您的验证码为:" + vcode);
                   // userService.addMessage(cellphone,"[smile]您的验证码为:" + vcode,  Constant.findpwd);
                    String param =  String.format(Constant.findpwd_param,  vcode);
                    userService.addMessage(cellphone,param,   Constant.findpwd_templatecode, Constant.findpwd );

                    logonService.addSMS(u.getUser_id(), cellphone, vcode, Constant.findpwd, new Date());

                    result.put(Constant.status, 1);
                    result.put(Constant.result, "验证码已发送");

                }


            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/checkVerificationCode", method = RequestMethod.POST)
    public Map checkVerificationCode(@RequestBody Map body) {

        //  check phone number  and vcode ,crated in  5minutes

        Map<String, Object> result = new HashMap<String, Object>();
        try {


            String  cellphone =(String)body.get("cellphone");
            String  vcode =(String)body.get("vcode");

            if (logonService.checkSMS(cellphone, vcode)) {

                result.put(Constant.status, 1);
                result.put(Constant.result, "验证通过");

            } else {
                result.put(Constant.status, 0);
                result.put(Constant.result, "验证码输入错误");
            }


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;

    }


    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    public Map logon(@RequestBody Map body) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            String  pwd =(String)  body.get("pwd");
            String   cellphone=(String)  body.get("cellphone");

            if (logonService.logon(pwd, cellphone)) {

                result.put(Constant.status, 1);
                User user = logonService.getUserByPhoneNumber(cellphone);
                result.put(Constant.result, user.getToken());
                result.put("userInfo", user);
                //result.put("banner",  companyService.getPicUrl( user.getCompany_id(), "banner"));
               // result.put("logo",  companyService.getPicUrl( user.getCompany_id(), "logo"));

                 logonService.addLogonHistory(user.getUser_id());

            } else {

                result.put(Constant.status, 0);
                result.put(Constant.result, "用户名或密码错误");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/updatePasswordByCellphone", method = RequestMethod.POST)
    public Map updatePasswordByCellphone(@RequestBody Map body) {

        // 先检查验证码 然后再改密码
        Map<String, Object> result = new HashMap<String, Object>();
        try {


            String  pwd =(String)  body.get("pwd");
            String   cellphone=(String)  body.get("cellphone");
            String   vcode=(String)  body.get("vcode");

            if (logonService.checkSMS(cellphone, vcode)) {
                logonService.updatePwdByCellphone(pwd, cellphone);

                result.put(Constant.status, 1);
                result.put(Constant.result, "密码更改成功");

            } else {
                result.put(Constant.status, 0);
                result.put(Constant.result, "验证码输入错误");
            }


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

  //暂时没用到
    /*
    @RequestMapping(value = "/getUserByID", method = RequestMethod.GET)
    public Map getUserByID(int id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            User u = logonService.getUserByID(id);

            result.put(Constant.status, 1);
            result.put(Constant.result, u);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());;
        }
        return result;
    }*/


    @RequestMapping(value = "/back/logon", method = RequestMethod.POST)
    public Map backlogon(@RequestBody Map body) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            String  pwd =(String)  body.get("pwd");
            String   cellphone=(String)  body.get("cellphone");

            if (logonService.backlogon(pwd, cellphone)) {

                result.put(Constant.status, 1);
                User user = logonService.getUserByPhoneNumber(cellphone);
                result.put(Constant.result, user.getToken());
                result.put("userInfo", user);

            } else {

                result.put(Constant.status, 0);
                result.put(Constant.result, "用户名或密码错误");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/back/GetLogonHistoryList", method = RequestMethod.GET)
    public Map backGetLogonHistoryList(@RequestParam String fullname, @RequestParam String department,
                               @RequestParam String area, @RequestParam String start, @RequestParam String end,@RequestParam int skip,@RequestParam int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            LogonHistory  h=new LogonHistory();
            if( ! Helper.isNullOrEmpty(fullname)  )
                 h.setFull_name(fullname);
            if( ! Helper.isNullOrEmpty(department)  )
                h.setDepartment(department);
            if( ! Helper.isNullOrEmpty(area)  )
                h.setArea(area);
            if( ! Helper.isNullOrEmpty(start)  )
                h.setStart(start);
            if( ! Helper.isNullOrEmpty(end)  )
                h.setEnd(end);


                h.setSkip(skip);
                h.setTake(take);
                h.setCompany_id( user.getCompany_id());

            List<LogonHistory> lst=   logonService.backGetLogonHistoryList(h);
            int total =    logonService.backGetLogonHistoryListCount(h);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total, total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/admin/logon", method = RequestMethod.POST)
    public Map adminlogon(@RequestBody Map body) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            String  pwd =(String)  body.get("pwd");
            String   cellphone=(String)  body.get("cellphone");

            if (logonService.adminlogon(pwd, cellphone)) {

                result.put(Constant.status, 1);
                User user = logonService.getUserByPhoneNumber(cellphone);
                result.put(Constant.result, user.getToken());
                result.put("userInfo", user);

            } else {

                result.put(Constant.status, 0);
                result.put(Constant.result, "用户名或密码错误");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




}
