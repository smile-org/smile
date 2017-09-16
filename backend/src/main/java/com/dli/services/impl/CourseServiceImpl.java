package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.repositories.CourseRepo;
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
    public boolean courseCollected(int courseid, int userid) {
        if (courseRepo.countCourseCollection(courseid, userid) > 0)
            return true;
        else
            return false;
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


}
