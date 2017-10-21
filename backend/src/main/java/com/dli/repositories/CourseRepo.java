package com.dli.repositories;


import com.dli.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CourseRepo {

    List<CourseCategory> getCourseCategoryList(@Param("companyid")int  companyid, @Param("skip")int  skip , @Param("take")int take  );

    List<Course>  getCourseList(  @Param("userid") int userid,  @Param("cateid")int cateid,   @Param("skip")int skip,  @Param("take")int take);

    Course  getCourseByID(@Param("id") int id  ,@Param("userid") int userid  ) ;


    void  addCourseComment( @Param("userid")int userid ,@Param("courseid")int courseid,@Param("star")int star,@Param("comment")String comment,@Param("now")Date now );

    List<CourseComment> getCourseCommentList(  @Param("courseid" ) int courseid  );

    int  getCourseCommentListCount(int courseid );

    void  deleteCommentById( @Param("id")int id );

    List<CourseContent>   getCourseContentListByID(int id);

    CourseLearnProgress  getLearnProgress( @Param("userid")  int userid,  @Param("contentid")    int contentid );

    void addLearnProgress( @Param("courseid")  int  courseid,  @Param("contentid")  int   contentid   ,  @Param("userid")    int  userid);

    void  updateLearnProgress(  @Param("userid")  int userid,  @Param("contentid")  int contentid);

    List<SearchResult>  searchCourse(@Param("companyid") int companyid,  @Param("keyword")String keyword,   @Param("skip") int skip,@Param("take")  int take);

    List<Course>  getCourseListByExamid( int  examid);

    List<Course>  getMyCourseListInprocess( @Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<Course>  getMyCourseListFinished(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<Course>  getMyCourseListInTask(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

     int   getStudyCourseCountByUserid(  int  userid);

    int  getCurrentMonthStudyCourseCountByUserid( int userid);

      int   getLastStudyDaysCountByUserid(  int userid);

      List<Course> getMyCollectionList( @Param("userid") int userid,  @Param("skip")int  skip , @Param("take")int take );

      List<Course>  getFirstPageCourseList( @Param("scope") String scope  ,  @Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);


    void   backAddCourseCategory( CourseCategory cate);

    void     backUpdateCourseCategoryIcon( @Param("icon")String icon, @Param("cateid") int cateid);

     CourseCategory   backGetCourseCategoryByID(int cateid);

    void     backUpdateCourseCategoryName( @Param("catename")String catename, @Param("cateid") int cateid);

    void  backDisableCourseCategory(int cateid);

    void  backAddCourseContent(CourseContent  c);


    void  backUpdateCourseContentPath(@Param("path")String  path,@Param("id")int id);
    void  backUpdateCourseContentLink(@Param("link")String  link,@Param("id")int id);
    void  backUpdateCourseContentCourseID(@Param("courseid")int courseid,@Param("id")int id);

    void   backUpdateCourseContent(CourseContent  c);

    void   backDisableCourseContent(int contentid);

    CourseContent backGetCourseContentByID(int contentid);

    void   backDeleteCourseWhilteListByCourseID( int courseid);

    void     backAddCourseWhilteListMapping(  @Param("courseid") int  courseid, @Param("userid") int   userid );

    List<User>  backGetCourseWhiteListByCourseID(int courseid);

    void   backAddCourse(Course  c);

    void   backUpdateCourseIconAndPic( Course  c);

    void     backUpdateCoursePublishStatus(@Param("status")int  status ,@Param("courseid") int courseid );

    Course     backGetCourseByID(int courseid);

    void   backUpdateCourse(Course  c);

    List<backCourse > backGetCourseList(backCourse   bc);

    int  backGetCourseListCount(backCourse   bc);

    List<UserLearnProgress> backGetUserLearnProgressList(UserLearnProgress  ulp);

    int backGetUserLearnProgressListCount(UserLearnProgress ulp);

    void   backDisableCourse( int   courseid);
}
