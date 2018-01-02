package com.dli.entities;

import java.util.Date;

public class TaskAndLearnStatus {

    private  int task_id;
    private int  user_id;

    private   String task_title;

    private   String      task_scope;

    private Date expiration_date;
    private     String     learn_percentage;


    private     int 	count_down;
    private  String   learn_status;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getLearn_percentage() {
        return learn_percentage;
    }

    public void setLearn_percentage(String learn_percentage) {
        this.learn_percentage = learn_percentage;
    }

    public int getCount_down() {
        return count_down;
    }

    public void setCount_down(int count_down) {
        this.count_down = count_down;
    }

    public String getLearn_status() {
        return learn_status;
    }

    public void setLearn_status(String learn_status) {
        this.learn_status = learn_status;
    }


}
