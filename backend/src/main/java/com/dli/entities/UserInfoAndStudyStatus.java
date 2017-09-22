package com.dli.entities;


public class UserInfoAndStudyStatus {

    private   String  full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLastStudy() {
        return lastStudy;
    }

    public void setLastStudy(int lastStudy) {
        this.lastStudy = lastStudy;
    }

    public int getCourseCountThisMonth() {
        return courseCountThisMonth;
    }

    public void setCourseCountThisMonth(int courseCountThisMonth) {
        this.courseCountThisMonth = courseCountThisMonth;
    }

    public int getCoursecountTotal() {
        return coursecountTotal;
    }

    public void setCoursecountTotal(int coursecountTotal) {
        this.coursecountTotal = coursecountTotal;
    }

    private  String   avatar;
    private   int  lastStudy;
    private     int courseCountThisMonth;
    private   int   coursecountTotal;
}
