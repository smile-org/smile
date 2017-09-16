package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.CollectService;
import com.dli.services.CourseService;
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
public class CourseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseService courseService;


    @Autowired
    private CollectService collectService;

    @Autowired
    private LogonService logonService;


    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    public Map getCategoryList(int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<CourseCategory> lst = courseService.getCourseCategoryList(user.getCompany_id(), skip, take);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);



        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCoursesList", method = RequestMethod.GET)
    public Map getCoursesList(int cateid, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Course> lst = courseService.getCourseList(user.getUser_id(), cateid, skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCourseByID", method = RequestMethod.GET)
    public Map getCourseByID(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            Course c = courseService.getCourseByID(courseid, user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, c);



        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCourseContentListByID", method = RequestMethod.GET)
    public Map getCourseContentListByID(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<CourseContent> lst = courseService.getCourseContentListByID(courseid);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/clickCourseContent", method = RequestMethod.GET)
    public Map clickCourseContent(int courseid, int contentid, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String message = courseService.ClickCourseContent(courseid, contentid, user.getUser_id());
            result.put(Constant.status, 1);
            result.put(Constant.result, message);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
        return result;
    }

    //用不到了
    @RequestMapping(value = "/courseCollected", method = RequestMethod.GET)
    public Map courseCollected(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            boolean r = courseService.courseCollected(courseid, user.getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, r);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addCourseCollection", method = RequestMethod.GET)
    public Map addCourseCollection(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            boolean r = courseService.courseCollected(courseid, user.getUser_id());

            if (r) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复收藏");

            } else {
                collectService.addCollection(user.getUser_id(), Helper.GetItemType(1), courseid);

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


    @RequestMapping(value = "/cancelCourseCollection", method = RequestMethod.GET)
    public Map cancelCourseCollection(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            collectService.cancelCollection(user.getUser_id(), Helper.GetItemType(1), courseid);
            result.put(Constant.status, 1);
            result.put(Constant.result, "取消收藏成功");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/addCourseComment", method = RequestMethod.POST)
    public Map addCourseComment(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int courseid = (int) body.get("courseid");
            int star = (int) body.get("star");
            String comment = (String) body.get("comment");

            courseService.addCourseComment(user.getUser_id(), courseid, star, comment, new Date());

            result.put(Constant.status, 1);
            result.put(Constant.result, "评价成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getCourseCommentList", method = RequestMethod.GET)
    public Map getCourseCommentList(int courseid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<CourseComment> lst = courseService.getCourseCommentList(courseid);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/deleteCommentById", method = RequestMethod.GET)
    public Map deleteCommentById(int commentid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            courseService.deleteCommentById(commentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 1);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }
}
