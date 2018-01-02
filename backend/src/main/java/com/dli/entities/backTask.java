package com.dli.entities;

import java.util.Date;

public class backTask {
   private   int   task_id;
    private     String     task_title;
    private   String  task_scope;
    private Date expiration_date;
    private  boolean ispublished;

    private  String finish_status;

    private  Date start_date;
    private  Date end_date;

    private        int skip;
    private              int take;



    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask_scope() {
        return task_scope;
    }

    public void setTask_scope(String task_scope) {
        this.task_scope = task_scope;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public boolean isIspublished() {
        return ispublished;
    }

    public void setIspublished(boolean ispublished) {
        this.ispublished = ispublished;
    }

    public String getFinish_status() {
        return finish_status;
    }

    public void setFinish_status(String finish_status) {
        this.finish_status = finish_status;
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

}
