package com.dli.entities;


import java.util.Date;

public class backEnrollment {

    private int period_id;
    private int enrollment_id;
    private String title;

    private String teacher;
    private String start;

    private String end;

    private Date startCondition;
    private Date endCondition;

    private int count;

    private   int  countThisPeriod;
    private   int   dateCountNextPeriod;

    private  boolean ispublished;


    private   int   company_id;
    private   int skip;
    private  int take;

    public int getCountThisPeriod() {
        return countThisPeriod;
    }

    public void setCountThisPeriod(int countThisPeriod) {
        this.countThisPeriod = countThisPeriod;
    }

    public int getDateCountNextPeriod() {
        return dateCountNextPeriod;
    }

    public void setDateCountNextPeriod(int dateCountNextPeriod) {
        this.dateCountNextPeriod = dateCountNextPeriod;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    public boolean isIspublished() {
        return ispublished;
    }

    public void setIspublished(boolean ispublished) {
        this.ispublished = ispublished;
    }


    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }


    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
    }


    public Date getStartCondition() {
        return startCondition;
    }

    public void setStartCondition(Date startCondition) {
        this.startCondition = startCondition;
    }

    public Date getEndCondition() {
        return endCondition;
    }

    public void setEndCondition(Date endCondition) {
        this.endCondition = endCondition;
    }


}
