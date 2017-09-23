package com.dli.controllers;


import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class LogonController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogonService logonService;

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
                String vcode = String.valueOf(Helper.getRandNum(0, 999999));
                Helper.SendMessage(cellphone, "[smile]您的验证码为:" + vcode);
                logonService.addSMS(u.getUser_id(), cellphone, vcode, Constant.findpwd, new Date());

                result.put(Constant.status, 1);
                result.put(Constant.result, "验证码已发送");

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


    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Map updatePassword(@RequestBody Map body) {

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
    }

}
