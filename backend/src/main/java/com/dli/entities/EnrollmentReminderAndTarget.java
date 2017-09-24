package com.dli.entities;


public class EnrollmentReminderAndTarget {

    private  int period_id;
    private  int target_period_id;

    private  String teacher;
    private  String title;

    public int getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public int getTarget_period_id() {
        return target_period_id;
    }

    public void setTarget_period_id(int target_period_id) {
        this.target_period_id = target_period_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
