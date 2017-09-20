package com.dli.services;


import com.dli.entities.*;


import java.util.Date;
import java.util.List;

public interface CourseService {

    List<CourseCategory> getCourseCategoryList(int  companyid, int  skip , int take  );

    List<Course>  getCourseList(int userid, int cateid, int skip, int take);

    Course  getCourseByID(int id  , int userid );


    void  addCourseComment(int userid ,int courseid,int star,String comment,Date now );

    List<CourseComment> getCourseCommentList(int courseid );

    void  deleteCommentById( int id );

    List<CourseContent>   getCourseContentListByID(int id);

    String ClickCourseContent( int courseid, int contentid ,int userid);

    List<Course>  searchCourse(String keyword);


    List<Course>  getCourseListByExamid( int  examid);
}
