package com.dli.entities;

import java.util.Date;

public class Course {

    private int course_id;
    private int category_id;
    private String title;
    private int principal_user_id;
    private String department;
    private Date expiration_date;
    private String intro;
    private String icon;
    private String pic;
    private int type_id;
    private  boolean ispublished;
    private int company_id;
    private boolean indicator;
    private Date created_at;
    private Date updated_at;

    //append property
    private  int study_count;
    private  int collect_count;
    private   String  principal_user_idName;
    private   int courseCollected;

    public int getCourseCollected() {
        return courseCollected;
    }

    public void setCourseCollected(int courseCollected) {
        this.courseCollected = courseCollected;
    }

    public String getPrincipal_user_idName() {
        return principal_user_idName;
    }

    public void setPrincipal_user_idName(String principal_user_idName) {
        this.principal_user_idName = principal_user_idName;
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

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrincipal_user_id() {
        return principal_user_id;
    }

    public void setPrincipal_user_id(int principal_user_id) {
        this.principal_user_id = principal_user_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
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

    public boolean isIspublished() {
        return ispublished;
    }

    public void setIspublished(boolean ispublished) {
        this.ispublished = ispublished;
    }

}
