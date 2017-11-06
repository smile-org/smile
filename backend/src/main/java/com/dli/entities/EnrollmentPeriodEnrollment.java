package com.dli.entities;


import java.util.Date;

public class EnrollmentPeriodEnrollment {


    private int period_id;
    private int enrollment_id;
    private String teacher;
    private int count;
    private Date start_date;
    private Date end_date;
    private String location;
    private boolean indicator;
    private Date created_at;
    private Date updated_at;


    //enrollment property
    private String title;
    private String intro;
    private String icon;
    private String pic;
    private int company_id;

    //append property
    private  int study_count;
    private  int collect_count;
    private   int  enrollmentCollected;
    private  int left_count;
    private   int  isFinished;
    private   int isReminderAdded;
    private   int  isEnrollemntAdded;

    private   double  star;

    public int getIsEnrollemntAdded() {
        return isEnrollemntAdded;
    }

    public void setIsEnrollemntAdded(int isEnrollemntAdded) {
        this.isEnrollemntAdded = isEnrollemntAdded;
    }

    public int getIsReminderAdded() {
        return isReminderAdded;
    }

    public void setIsReminderAdded(int isReminderAdded) {
        this.isReminderAdded = isReminderAdded;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }


    public int getLeft_count() {
        return left_count;
    }

    public void setLeft_count(int left_count) {
        this.left_count = left_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }



    public int getStudy_count() {
        return study_count;
    }

    public void setStudy_count(int study_count) {
        this.study_count = study_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public int getEnrollmentCollected() {
        return enrollmentCollected;
    }

    public void setEnrollmentCollected(int enrollmentCollected) {
        this.enrollmentCollected = enrollmentCollected;
    }


    public int getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isIndicator() {
        return indicator;
    }

    public void setIndicator(boolean indicator) {
        this.indicator = indicator;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }



    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

}
