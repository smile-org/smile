package com.dli.services;


import com.dli.entities.*;


import java.util.Date;
import java.util.List;

public interface CourseService {

    List<CourseCategory> getCourseCategoryList(int  companyid, int  skip , int take  );

    List<Course>  getCourseList(int userid, int cateid, int skip, int take);

    Course  getCourseByID(int id);

    void   addCourseCollection(  int userid, int courseid,  Date now);

    boolean courseCollected( int courseid,  int userid);

    void  addCourseComment(int userid ,int courseid,int star,String comment,Date now );

    List<CourseComment> getCourseCommentList();

    void  deleteCommentById( int id );

    List<CourseContent>   getCourseContentListByID(int id);

    String ClickCourseContent( int courseid, int contentid ,int userid);
}
