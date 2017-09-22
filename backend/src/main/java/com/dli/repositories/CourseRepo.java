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

    void  deleteCommentById( @Param("id")int id );

    List<CourseContent>   getCourseContentListByID(int id);

    CourseLearnProgress  getLearnProgress( @Param("userid")  int userid,  @Param("contentid")    int contentid );

    void addLearnProgress( @Param("courseid")  int  courseid,  @Param("contentid")  int   contentid   ,  @Param("userid")    int  userid);

    void  updateLearnProgress(  @Param("userid")  int userid,  @Param("contentid")  int contentid);

    List<Course>  searchCourse( @Param("keyword") String keyword);

    List<Course>  getCourseListByExamid( int  examid);

    List<Course>  getMyCourseListInprocess( @Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<Course>  getMyCourseListFinished(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<Course>  getMyCourseListInTask(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

     int   getStudyCourseCountByUserid(  int  userid);

    int  getCurrentMonthStudyCourseCountByUserid( int userid);

      int   getLastStudyDaysCountByUserid(  int userid);

      List<Course> getMyCollectionList( @Param("userid") int userid,  @Param("skip")int  skip , @Param("take")int take );

      List<Course>  getFirstPageCourseList( @Param("scope") String scope  ,  @Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);


}
