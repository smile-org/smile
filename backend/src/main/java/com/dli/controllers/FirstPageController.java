package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class FirstPageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private LogonService logonService;

    @Autowired
    private CourseService courseService;





    @RequestMapping(value = "/getFirstPageCourseList", method = RequestMethod.GET)
    public Map getFirstPageCourseList( int scopeid, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String  scope="";

            if(scopeid==1){
                   scope= Helper.getCurrentWeekBegin();
            }
            else  if(scopeid==2){
                scope= Helper.getCurrentMonthBegin();
            }
            else if (scopeid==3){
                scope="2010-01-01";
            }




            List<Course> lst = courseService.getFirstPageCourseList( scope,  user.getCompany_id(), user.getUser_id(), skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




}
