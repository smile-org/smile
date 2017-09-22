package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.CollectService;
import com.dli.services.CourseService;
import com.dli.services.EnrollmentService;
import com.dli.services.LogonService;
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
public class EnrollmentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EnrollmentService enrollmentService;


    @Autowired
    private CollectService collectService;

    @Autowired
    private LogonService logonService;


    @RequestMapping(value = "/getEnrollmentPeriodListInprocess", method = RequestMethod.GET)
    public Map getEnrollmentPeriodListInprocess(  int skip , int take , @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentPeriodEnrollment> lst = enrollmentService.getEnrollmentPeriodListInprocess( user.getCompany_id() ,skip  ,take);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getEnrollmentPeriodListFinished", method = RequestMethod.GET)
    public Map getEnrollmentPeriodListFinished(  int skip ,int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentPeriodEnrollment> lst = enrollmentService.getEnrollmentPeriodListFinished(  user.getUser_id(), user.getCompany_id() ,skip  ,take);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/enrollmentReminderAdded", method = RequestMethod.GET)
    public Map    enrollmentReminderAdded(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int count = enrollmentService.getReminderMappingCount(periodid, user.getUser_id());
            int added= 0;
            if(count >0) {

                added=1;
            }


            result.put(Constant.status, 1);
            result.put(Constant.result, added);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addReminderMapping", method = RequestMethod.GET)
    public Map addReminderMapping(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int count = enrollmentService.getReminderMappingCount(periodid, user.getUser_id());
            if(count >0) {

                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复添加开班提醒");
            }
            else
            {
                enrollmentService.addReminderMapping(periodid, user.getUser_id());
                result.put(Constant.status, 1);
                result.put(Constant.result, "该报名已加入开班提醒");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getEnrollmentPeriodByID", method = RequestMethod.GET)
    public Map getEnrollmentPeriodByID(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            EnrollmentPeriodEnrollment  entity = enrollmentService.getEnrollmentPeriodByID(periodid  ,  user.getUser_id());


                result.put(Constant.status, 1);
                result.put(Constant.result, entity);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addEnrollmentMapping", method = RequestMethod.GET)
    public Map addEnrollmentMapping(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int count= enrollmentService.getEnrollmentMappingCount(periodid ,user.getUser_id() );

            if(count >0)
            {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复报名");
            }
            else {
                enrollmentService.addEnrollmentMapping(periodid, user.getUser_id());


                result.put(Constant.status, 1);
                result.put(Constant.result, "报名成功");
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getEnrollmentContentListByID", method = RequestMethod.GET)
    public Map getEnrollmentContentListByID(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentContent>   contentLst=   enrollmentService.getEnrollmentContentListByID(periodid);


            result.put(Constant.status, 1);
            result.put(Constant.result, contentLst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/addEnrollmentCollection", method = RequestMethod.GET)
    public Map addEnrollmentCollection(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            boolean r = collectService.itemCollected( "enrollment"  , periodid, user.getUser_id());

            if (r) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复收藏");

            } else {
                collectService.addCollection(user.getUser_id(), Helper.GetItemType(3), periodid);

                result.put(Constant.status, 1);
                result.put(Constant.result, "收藏成功");

            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/cancelEnrollmentCollection", method = RequestMethod.GET)
    public Map cancelEnrollmentCollection(int periodid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            collectService.cancelCollection(user.getUser_id(), Helper.GetItemType(3), periodid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "取消收藏成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addEnrollmentComment", method = RequestMethod.POST)
    public Map addEnrollmentComment(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int enrollmentid = (int) body.get("enrollmentid");
            int star = (int) body.get("star");
            String comment = (String) body.get("comment");

            enrollmentService.addEnrollmentComment(user.getUser_id(), enrollmentid, star, comment);

            result.put(Constant.status, 1);
            result.put(Constant.result, "评价成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getEnrollmentCommentList", method = RequestMethod.GET)
    public Map getEnrollmentCommentList(int enrollmentid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentComment> lst = enrollmentService.getEnrollmentCommentList(enrollmentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

}
