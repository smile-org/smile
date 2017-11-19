package com.dli.entities;


import java.util.Date;

public class Exam {

    private int exam_id;
    private String exam_num;
    private String exam_title;
    private int manager_id;
    private Date start_date;
    private Date end_date;
    private int tries_limit;
    private int pass_score;
    private int time_limit;
    private String intro;


    private String icon;
    private String pic;
    private  boolean ispublished;
    private int company_id;
    private boolean indicator;
    private Date created_at;
    private Date updated_at;

    private  int study_count;
    private  int collect_count;
    private   String  manager_idName;
    private   int examCollected;
    private  int history_id;

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected  String status;  //服务端判断是否开始 和继续

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

    public String getManager_idName() {
        return manager_idName;
    }

    public void setManager_idName(String manager_idName) {
        this.manager_idName = manager_idName;
    }

    public int getExamCollected() {
        return examCollected;
    }

    public void setExamCollected(int examCollected) {
        this.examCollected = examCollected;
    }



    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_num() {
        return exam_num;
    }

    public void setExam_num(String exam_num) {
        this.exam_num = exam_num;
    }

    public String getExam_title() {
        return exam_title;
    }

    public void setExam_title(String exam_title) {
        this.exam_title = exam_title;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
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

    public int getTries_limit() {
        return tries_limit;
    }

    public void setTries_limit(int tries_limit) {
        this.tries_limit = tries_limit;
    }

    public int getPass_score() {
        return pass_score;
    }

    public void setPass_score(int pass_score) {
        this.pass_score = pass_score;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
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
