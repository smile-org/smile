package com.dli.services;


import com.dli.entities.*;

import java.util.List;

public interface EnrollmentService {

    List<EnrollmentPeriodEnrollment> getEnrollmentPeriodListInprocess(int companyid , int skip , int take );
    List<EnrollmentPeriodEnrollment> getEnrollmentPeriodListFinished(int userid,int companyid, int skip , int take);
    int getReminderMappingCount( int periodid  ,   int userid   );
    void   addReminderMapping( int periodid  ,   int userid );
    EnrollmentPeriodEnrollment  getEnrollmentPeriodByID(int periodid   ,int userid);

    int getEnrollmentMappingCount( int periodid  ,  int userid   );

    void   addEnrollmentMapping( int periodid  ,  int userid );
    List<EnrollmentContent> getEnrollmentContentListByID(int periodid);

    void   addEnrollmentComment( int  userid  , int   enrollmentid , int star  ,String  comment);
    List<EnrollmentComment>  getEnrollmentCommentList( int enrollmentid);

    List<EnrollmentPeriodEnrollment> getMyEnrollmentListEnrolled(int  companyid,  int userid ,  int  skip  ,  int take);

    List<EnrollmentPeriodEnrollment>  getMyEnrollmentListInReminder(int  companyid,  int userid ,  int  skip  ,  int take);

    List<EnrollmentPeriodEnrollment> getMyCollectionList( int userid, int  skip , int take );

    List<EnrollmentReminderAndTarget>   getReminderList( int userid);
}
