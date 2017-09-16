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

    Course  getCourseByID(@Param("id") int id);

    void   addCourseCollection( @Param("userid") int userid,  @Param("courseid")int courseid,  @Param("now")Date now);

    int countCourseCollection(@Param("courseid") int courseid,  @Param("userid")int userid);

    void  addCourseComment( @Param("userid")int userid ,@Param("courseid")int courseid,@Param("star")int star,@Param("comment")String comment,@Param("now")Date now );

    List<CourseComment> getCourseCommentList();

    void  deleteCommentById( @Param("id")int id );

    List<CourseContent>   getCourseContentListByID(int id);

    CourseLearnProgress  getLearnProgress( @Param("userid")  int userid,  @Param("contentid")    int contentid );

    void addLearnProgress( @Param("courseid")  int  courseid,  @Param("contentid")  int   contentid   ,  @Param("userid")    int  userid);

    void  updateLearnProgress(  @Param("userid")  int userid,  @Param("contentid")  int contentid);
}
