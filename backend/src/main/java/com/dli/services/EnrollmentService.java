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

    void   updateReadReminder(   int periodid,    int userid  );

    List<SearchResult>  searchEnrollment(int companyid,  String keyword,    int skip,  int take);

    List< backEnrollment>  backGetEnrollmentList(backEnrollment  be);

    int backGetEnrollmentListCount(backEnrollment  be);

    List<EnrollmentComment>   backGetEnrollmentCommentList(  int enrollmentid, int skip ,int take);

    int   backGetEnrollmentCommentListCount(  int  enrollmentid );

    List< backEnrollment>   backGetEnrollmentResultList(backEnrollment  be);

    int   backGetEnrollmentResultListCount(backEnrollment  be);

    List<User>   backGetUserListThisEnrollmentPeriod(   int periodid , int skip,  int take);

    int backGetUserListThisEnrollmentPeriodCount(int periodid);


    List<User>   backGetUserListNextEnrollmentPeriod(   int periodid ,  int skip,  int take);

    int backGetUserListNextEnrollmentPeriodCount(int periodid);

    void    backDisableEnrollmentPeriod(int periodid);

    EnrollmentEditPageInfo  backGetEnrollmentEditPageInfoByEnrollmentID(int enrollmentid);

    EnrollmentEditPageInfo  backGetEnrollmentEditPageInfoByPeriodID(int   periodid);

    void  backAddEnrollment( Enrollment     e);

    void   backAddEnrollmentPeriod(EnrollmentPeriod   ep);

    void   backAddEnrollmentContent(  EnrollmentContent   ec );

    void   backUpdateEnrollmentIconAndPic( Enrollment  e );

    void  backUpdateEnrollment(Enrollment e);

    void   backUpdateEnrollmentPeriod( EnrollmentPeriod  ep);


    void  backSaveContentList(int  enrollmentid,   List<EnrollmentContent>   contentList);
}
