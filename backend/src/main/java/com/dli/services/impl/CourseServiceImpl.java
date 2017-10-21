package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.repositories.CourseRepo;
import com.dli.repositories.UserRepo;
import com.dli.services.CourseService;
import com.dli.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public int getCourseCommentListCount(int courseid) {
       return   courseRepo.getCourseCommentListCount(courseid);
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
    public List<SearchResult> searchCourse(int companyid, String keyword, int skip, int take) {
        return courseRepo.searchCourse(companyid, keyword, skip, take);
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

    @Override
    public void backAddCourseCategory(CourseCategory cate) {
          courseRepo.backAddCourseCategory(cate);
    }

    @Override
    public void backUpdateCourseCategoryIcon(String icon, int cateid) {
        courseRepo.backUpdateCourseCategoryIcon(icon,cateid);
    }

    @Override
    public CourseCategory backGetCourseCategoryByID(int cateid) {
        return courseRepo.backGetCourseCategoryByID(cateid);
    }

    @Override
    public void backUpdateCourseCategoryName(String catename, int cateid) {
        courseRepo.backUpdateCourseCategoryName(catename,cateid);
    }

    @Override
    public void backDisableCourseCategory(int cateid) {
        courseRepo.backDisableCourseCategory(cateid);
    }

    @Override
    public void backAddCourseContent(CourseContent c) {
        courseRepo.backAddCourseContent(c);
    }

    @Override
    public void backUpdateCourseContentPath(String path ,int id) {
        courseRepo.backUpdateCourseContentPath(path , id);
    }

    @Override
    public void backUpdateCourseContentLink(String link ,int id) {
        courseRepo.backUpdateCourseContentLink(link , id);
    }

    @Override
    public void backUpdateCourseContentCourseID(int courseid ,int id) {
        courseRepo.backUpdateCourseContentCourseID(courseid , id);
    }

    @Override
    public void backUpdateCourseContent(CourseContent c) {
        courseRepo. backUpdateCourseContent(c);
    }

    @Override
    public void backDisableCourseContent(int contentid) {
        courseRepo.backDisableCourseContent(contentid);
    }

    @Override
    public CourseContent backGetCourseContentByID(int contentid) {
        return courseRepo.backGetCourseContentByID(contentid);
    }

    @Override
    public void backSaveCourseWhilteList(int courseid, String[] useridList) {
        courseRepo.backDeleteCourseWhilteListByCourseID(courseid);

        for( String  userid  : useridList   ){
            courseRepo.backAddCourseWhilteListMapping(courseid, Integer.parseInt( userid));
        }
    }


    @Value("${defaultcourseicon}")
    private String defaultcourseicon;

    @Value("${defaultcoursepic}")
    private String defaultcoursepic;

    @Override
    public CourseEditPageInfo backGetCourseEditPageInfo(int companyid,int courseid ) {
        CourseEditPageInfo entity =new CourseEditPageInfo();

        entity.CategoryList = courseRepo.getCourseCategoryList(companyid, 0,  Constant.takeMax);
        entity.AdminList =userRepo.backGetCompanyAdminList( companyid );

        if(courseid >  0)
        {
            entity.CourseTobeEdit = courseRepo.backGetCourseByID(courseid);
            entity.ContentList =  courseRepo.getCourseContentListByID(courseid);
            entity.UserWhiteList =courseRepo.backGetCourseWhiteListByCourseID(courseid);
        }


        return entity;
    }

    @Override
    public void backAddCourse(Course c) {
        courseRepo.backAddCourse(c);
    }

    @Override
    public void backUpdateCourseIconAndPic(Course c) {
        courseRepo.backUpdateCourseIconAndPic(c);
    }

    @Override
    public void backUpdateCoursePublishStatus(int status, int courseid) {
        courseRepo.backUpdateCoursePublishStatus(status, courseid);
    }

    @Override
    public void backUpdateCourse(Course c) {
        courseRepo.backUpdateCourse(c);
    }

    @Override
    public   List<backCourse >  backGetCourseList(backCourse bc) {
        return courseRepo.backGetCourseList(bc);
    }

    @Override
    public int backGetCourseListCount(backCourse bc) {
        return   courseRepo.backGetCourseListCount(bc);
    }

    @Override
    public List<UserLearnProgress> backGetUserLearnProgressList(UserLearnProgress ulp) {
        return courseRepo.backGetUserLearnProgressList(ulp);
    }

    @Override
    public int backGetUserLearnProgressListCount(UserLearnProgress ulp) {
        return courseRepo.backGetUserLearnProgressListCount(ulp);
    }

    @Override
    public void backDisableCourse(int courseid) {
        courseRepo.backDisableCourse(courseid);
    }


}
