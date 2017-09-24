package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.*;
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
public class MyCenterController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private LogonService logonService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ExamService examService;

    @Autowired
    private EnrollmentService enrollmentService;


    @Autowired
    private CollectService collectService;



    @RequestMapping(value = "/getMyCourseListInprocess", method = RequestMethod.GET)
    public Map getMyCourseListInprocess( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Course> lst = courseService.getMyCourseListInprocess(   user.getCompany_id(), user.getUser_id(), skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getMyCourseListFinished", method = RequestMethod.GET)
    public Map getMyCourseListFinished( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Course> lst = courseService.getMyCourseListFinished( user.getCompany_id(),user.getUser_id(), skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getMyCourseListInTask", method = RequestMethod.GET)
    public Map getMyCourseListInTask( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Course> lst = courseService.getMyCourseListInTask( user.getCompany_id(),user.getUser_id(), skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getMyStudyStatus", method = RequestMethod.GET)
    public Map getMyStudyStatus(  @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            UserInfoAndStudyStatus  userInfoAndStudyStatus  = courseService.getMyStudyStatus(user.getUser_id());
            result.put(Constant.status, 1);
            result.put(Constant.result, userInfoAndStudyStatus);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getMyExamlistPassed", method = RequestMethod.GET)
    public Map getMyExamlistPassed( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Exam>  lst  = examService.getMyExamlistPassed( user.getCompany_id(), user.getUser_id(),skip,take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getMyExamlistNotPassed", method = RequestMethod.GET)
    public Map getMyExamlistNotPassed( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Exam>  lst  = examService.getMyExamlistNotPassed( user.getCompany_id(), user.getUser_id(),skip,take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getMyEnrollmentListEnrolled", method = RequestMethod.GET)
    public Map getMyEnrollmentListEnrolled( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentPeriodEnrollment>  lst  = enrollmentService.getMyEnrollmentListEnrolled( user.getCompany_id(), user.getUser_id(),skip,take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/getMyEnrollmentListInReminder", method = RequestMethod.GET)
    public Map getMyEnrollmentListInReminder( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<EnrollmentPeriodEnrollment>  lst  = enrollmentService.getMyEnrollmentListInReminder( user.getCompany_id(), user.getUser_id(),skip,take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getMyAppointmentList", method = RequestMethod.GET)
    public Map getMyAppointmentList( int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<Appointment>  lst  =  appointmentService.getMyAppointmentList(  user.getUser_id(),skip,take);
            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/getMyCollectionList", method = RequestMethod.GET)
    public Map getMyCollectionList( int typeid, int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            if(typeid==1) {

                List<Course> lst = courseService.getMyCollectionList  (user.getUser_id(), skip, take);
                result.put(Constant.status, 1);
                result.put(Constant.result, lst);
            }
            else if(typeid==2){

                List<Exam> lst = examService.getMyCollectionList  (user.getUser_id(), skip, take);
                result.put(Constant.status, 1);
                result.put(Constant.result, lst);
            }

            else if(typeid==3){
                List<EnrollmentPeriodEnrollment> lst = enrollmentService.getMyCollectionList  (user.getUser_id(), skip, take);
                result.put(Constant.status, 1);
                result.put(Constant.result, lst);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }
}
