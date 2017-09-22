package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.repositories.CourseRepo;
import com.dli.repositories.UserRepo;
import com.dli.services.CourseService;
import com.dli.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public List<CourseCategory> getCourseCategoryList(int companyid, int skip, int take) {
        return courseRepo.getCourseCategoryList(companyid, skip, take);
    }

    @Override
    public List<Course> getCourseList(int userid, int cateid, int skip, int take) {
        return courseRepo.getCourseList(userid, cateid, skip, take);
    }

    @Override
    public Course getCourseByID(int id,int userid ) {
        return courseRepo.getCourseByID(id, userid);
    }


    @Override
    public void addCourseComment(int userid, int courseid, int star, String comment, Date now) {
        courseRepo.addCourseComment(userid, courseid, star, comment, now);
    }

    @Override
    public List<CourseComment> getCourseCommentList(int courseid ) {
        return courseRepo.getCourseCommentList(courseid);
    }

    @Override
    public void deleteCommentById(int id) {
        courseRepo.deleteCommentById(id);
    }

    @Override
    public List<CourseContent> getCourseContentListByID(int id) {
        return   courseRepo.getCourseContentListByID(id);
    }

    @Override
    public String ClickCourseContent(int courseid, int contentid, int userid) {
       CourseLearnProgress  clp=  courseRepo.getLearnProgress(userid, contentid);

       if( clp !=null ) {
           if(clp.getStatus().equals("NotStarted" )) {
           courseRepo.updateLearnProgress(userid, contentid);
           return  "状态更新为Finished";
           }
           else {
               return  "该章节已学过,不用更新状态";
           }
       }
       else {

          List<CourseContent>    lst=  courseRepo.getCourseContentListByID(courseid);
          for(   CourseContent    content :   lst ){
              courseRepo.addLearnProgress(courseid, content.getContent_id(), userid);
          }

           courseRepo.updateLearnProgress(userid, contentid);

           return "该课程所有目录初始化完成";
       }

    }

    @Override
    public List<Course> searchCourse(String keyword) {
        return   courseRepo.searchCourse(keyword);
    }

    @Override
    public List<Course> getCourseListByExamid(int examid) {
        return  courseRepo.getCourseListByExamid(examid);
    }

    @Override
    public List<Course> getMyCourseListInprocess(int  companyid,int userid, int skip, int take) {
        return    courseRepo.getMyCourseListInprocess( companyid,userid, skip, take);
    }

    @Override
    public List<Course> getMyCourseListFinished(int  companyid,int userid, int skip, int take) {
        return courseRepo.getMyCourseListFinished( companyid,userid, skip, take);
    }

    @Override
    public List<Course> getMyCourseListInTask(int  companyid,int userid, int skip, int take) {
        return   courseRepo.getMyCourseListInTask( companyid,userid, skip, take);
    }

    @Override
    public UserInfoAndStudyStatus getMyStudyStatus(int userid) {
        User   user=     userRepo.getUserByUserID(userid);

        UserInfoAndStudyStatus result =new  UserInfoAndStudyStatus();
        result.setFull_name( user.getFull_name() );
        result.setAvatar(  user.getAvatar());

        result.setLastStudy(  courseRepo.getLastStudyDaysCountByUserid(userid)    );
        result.setCourseCountThisMonth( courseRepo.getCurrentMonthStudyCourseCountByUserid(userid)  );
        result.setCoursecountTotal( courseRepo.getStudyCourseCountByUserid(userid)    );

        return  result;
    }

    @Override
    public List<Course> getMyCollectionList(int userid, int skip, int take) {
        return   courseRepo.getMyCollectionList(userid, skip, take);
    }

    @Override
    public List<Course> getFirstPageCourseList(String scope, int companyid, int userid, int skip, int take) {
        return     courseRepo.getFirstPageCourseList(scope, companyid, userid, skip, take);
    }
}
