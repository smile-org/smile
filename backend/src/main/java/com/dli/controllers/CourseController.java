package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.CourseService;
import com.dli.services.DemoService;
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


    @RequestMapping(value = "/getCategoryList", method = RequestMethod.GET)
    public Map getCategoryList(int skip, int take) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            List<CourseCategory> lst = courseService.getCourseCategoryList(Helper.GetCurrentUser().getCompany_id(), skip, take);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/getCoursesList", method = RequestMethod.GET)
    public Map getCoursesList(int cateid, int skip, int take) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            List<Course> lst = courseService.getCourseList(Helper.GetCurrentUser().getUser_id(), cateid, skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/getCourseByID", method = RequestMethod.GET)
    public Map getCourseByID(int courseid) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            Course c = courseService.getCourseByID(courseid);

            result.put(Constant.status, 1);
            result.put(Constant.result, c);

            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/getCourseContentListByID", method = RequestMethod.GET)
    public Map getCourseContentListByID(int courseid) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            List<CourseContent> lst = courseService.getCourseContentListByID(courseid);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/clickCourseContent", method = RequestMethod.GET)
    public Map clickCourseContent(int courseid, int contentid) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            String message=  courseService.ClickCourseContent(courseid,contentid, Helper.GetCurrentUser().getUser_id());
            result.put(Constant.status, 1);
            result.put(Constant.result, message);
            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }


    @RequestMapping(value = "/courseCollected", method = RequestMethod.GET)
    public Map courseCollected(int courseid) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            boolean r = courseService.courseCollected(courseid, Helper.GetCurrentUser().getUser_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, r);


            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }


    @RequestMapping(value = "/addCourseCollection", method = RequestMethod.GET)
    public Map addCourseCollection(int courseid) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            boolean r = courseService.courseCollected(courseid, Helper.GetCurrentUser().getUser_id());

            if (r) {
                result.put(Constant.status, 0);
                result.put(Constant.result, "不能重复收藏");

            } else {
                courseService.addCourseCollection(Helper.GetCurrentUser().getUser_id(), courseid, new Date());

                result.put(Constant.status, 1);
                result.put(Constant.result, "收藏成功");

            }
            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }


    @RequestMapping(value = "/addCourseComment", method = RequestMethod.POST)
    public Map addCourseComment(@RequestBody Map body) {

        try {
            Map<String, Object> result = new HashMap<String, Object>();


            int courseid = (int) body.get("courseid");
            int star = (int) body.get("star");
            String comment = (String) body.get("comment");


            courseService.addCourseComment(Helper.GetCurrentUser().getUser_id(), courseid, star, comment, new Date());


            result.put(Constant.status, 1);
            result.put(Constant.result, "评价成功");

            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }


    @RequestMapping(value = "/getCourseCommentList", method = RequestMethod.GET)
    public Map getCourseCommentList() {

        try {
            Map<String, Object> result = new HashMap<String, Object>();

            List<CourseComment> lst = courseService.getCourseCommentList();

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }


    @RequestMapping(value = "/deleteCommentById", method = RequestMethod.GET)
    public Map deleteCommentById(int commentid) {

        try {
            Map<String, Object> result = new HashMap<String, Object>();

            courseService.deleteCommentById(commentid);

            result.put(Constant.status, 1);
            result.put(Constant.result, "删除成功");

            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

}
