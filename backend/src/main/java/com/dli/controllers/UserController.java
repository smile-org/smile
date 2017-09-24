package com.dli.controllers;

import com.dli.entities.Demo;
import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.services.DemoService;
import com.dli.services.LogonService;
import com.dli.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @Autowired
    private LogonService logonService;



    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Map updatePassword( @RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String   password =(String) body.get("password");
            userService.updatePasswordByUserid(password,  user.getUser_id());

            result.put(Constant.result, "密码更新成功");
            result.put(Constant.status, 1);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }


}
