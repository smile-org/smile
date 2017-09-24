package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.repositories.CollectRepo;
import com.dli.repositories.DemoRepo;
import com.dli.repositories.EnrollmentRepo;
import com.dli.services.DemoService;
import com.dli.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private CollectRepo collectRepo;

    @Override
    public List<EnrollmentPeriodEnrollment> getEnrollmentPeriodListInprocess(int companyid , int skip , int take ) {
        return enrollmentRepo.getEnrollmentPeriodListInprocess(companyid,skip,take);
    }

    @Override
    public List<EnrollmentPeriodEnrollment> getEnrollmentPeriodListFinished(int userid,int companyid, int skip , int take ) {
        return enrollmentRepo.getEnrollmentPeriodListFinished(userid,companyid,skip,take);
    }

    @Override
    public int getReminderMappingCount(int periodid, int userid) {
        return   enrollmentRepo.getReminderMappingCount(periodid, userid);
    }

    @Override
    public void addReminderMapping(int periodid, int userid) {
           enrollmentRepo.addReminderMapping(periodid,userid);
    }

    @Override
    public EnrollmentPeriodEnrollment getEnrollmentPeriodByID(int periodid ,int userid) {
        EnrollmentPeriodEnrollment  e= enrollmentRepo.getEnrollmentPeriodByID(periodid);

         int count = collectRepo.countCollection("enrollment",periodid,userid);
          int collect=0;
          if(count >0)
              collect=1;
        e.setEnrollmentCollected(collect);

        int reminderAdded =0;
        if(enrollmentRepo.getReminderMappingCount(periodid,userid) >0)
            reminderAdded=1;

        e.setIsReminderAdded(reminderAdded);


        int isFinished =0;
        if( e.getEnd_date().getTime() <=   new Date().getTime())
            isFinished=1;
        e.setIsFinished(isFinished);

        int isEnrollmentAdded=0;
        if(enrollmentRepo.getEnrollmentMappingCount(periodid, userid)>0)
            isEnrollmentAdded=1;
        e.setIsEnrollemntAdded(isEnrollmentAdded);

        return  e;
    }

    @Override
    public int getEnrollmentMappingCount(int periodid, int userid) {
        return   enrollmentRepo.getEnrollmentMappingCount(periodid,userid);
    }

    @Override
    public void addEnrollmentMapping(int periodid, int userid) {
        enrollmentRepo.addEnrollmentMapping(periodid, userid);
    }

    @Override
     public   List<EnrollmentContent> getEnrollmentContentListByID(int periodid) {
        return   enrollmentRepo.getEnrollmentContentListByID(periodid);
    }

    @Override
    public void addEnrollmentComment(int userid, int enrollmentid, int star, String comment) {
        enrollmentRepo.addEnrollmentComment(userid, enrollmentid, star, comment);
    }

    @Override
    public List<EnrollmentComment> getEnrollmentCommentList(int enrollmentid) {
        return    enrollmentRepo.getEnrollmentCommentList(enrollmentid);
    }

    @Override
    public List<EnrollmentPeriodEnrollment> getMyEnrollmentListEnrolled(int companyid, int userid, int skip, int take) {
        return  enrollmentRepo.getMyEnrollmentListEnrolled(companyid, userid, skip, take);
    }

    @Override
    public List<EnrollmentPeriodEnrollment> getMyEnrollmentListInReminder(int companyid, int userid, int skip, int take) {
        return  enrollmentRepo.getMyEnrollmentListInReminder(companyid, userid, skip, take);
    }

    @Override
    public List<EnrollmentPeriodEnrollment> getMyCollectionList(int userid, int skip, int take) {
        return   enrollmentRepo.getMyCollectionList(userid, skip, take);
    }

    @Override
    public List<EnrollmentReminderAndTarget> getReminderList(int userid) {
        List<EnrollmentReminderAndTarget>    lst=    enrollmentRepo.getReminderList(userid);

        for(   EnrollmentReminderAndTarget    ert : lst ){

            EnrollmentPeriodEnrollment period=  enrollmentRepo.getEnrollmentPeriodByID(ert.getPeriod_id());
            ert.setTeacher( period.getTeacher());

            ert.setTitle(period.getTitle());
        }
        return  lst;
    }

    @Override
    public void updateReadReminder(int periodid, int userid) {
        enrollmentRepo.updateReadReminder(periodid,userid);
    }

    @Override
    public List<SearchResult> searchEnrollment(int companyid, String keyword, int skip, int take) {
        return   enrollmentRepo.searchEnrollment(companyid, keyword, skip, take);
    }
}
