package com.dli.repositories;


import com.dli.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnrollmentRepo {

      List<EnrollmentPeriodEnrollment> getEnrollmentPeriodListInprocess(@Param("companyid")int companyid , @Param("skip")int skip , @Param("take")int take ) ;
      List<EnrollmentPeriodEnrollment> getEnrollmentPeriodListFinished( @Param("userid")int userid,  @Param("companyid")int companyid , @Param("skip")int skip , @Param("take")int take );
      int getReminderMappingCount(@Param("periodid") int periodid  , @Param("userid")  int userid   );
      void   addReminderMapping(@Param("periodid") int periodid  , @Param("userid")  int userid );

      EnrollmentPeriodEnrollment  getEnrollmentPeriodByID(int periodid);


      int getEnrollmentMappingCount(@Param("periodid") int periodid  , @Param("userid")  int userid   );
      void   addEnrollmentMapping(@Param("periodid") int periodid  , @Param("userid")  int userid );

     List<EnrollmentContent> getEnrollmentContentListByID(int periodid);


     void   addEnrollmentComment(@Param("userid") int  userid  , @Param("enrollmentid")int   enrollmentid , @Param("star")int star  ,@Param("comment")String  comment);
      List<EnrollmentComment>  getEnrollmentCommentList( int enrollmentid);

    List<EnrollmentPeriodEnrollment> getMyEnrollmentListEnrolled(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<EnrollmentPeriodEnrollment>  getMyEnrollmentListInReminder(@Param("companyid")int  companyid, @Param("userid") int userid , @Param("skip") int  skip  , @Param("take") int take);

    List<EnrollmentPeriodEnrollment> getMyCollectionList( @Param("userid") int userid,  @Param("skip")int  skip , @Param("take")int take );
}
