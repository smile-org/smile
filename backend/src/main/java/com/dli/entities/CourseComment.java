package com.dli.entities;

import java.util.Date;

public class CourseComment {
    public String getUser_idAvatar() {
        return user_idAvatar;
    }

    public void setUser_idAvatar(String user_idAvatar) {
        this.user_idAvatar = user_idAvatar;
    }

    public String getUser_idName() {
        return user_idName;
    }

    public void setUser_idName(String user_idName) {
        this.user_idName = user_idName;
    }

    private int comment_id;
    private int user_id;
    private int course_id;
    private int star;
    private String comments;
    private Date created_at;
    private  String user_idAvatar;
     private String  user_idName;
    private  boolean indicator;

    public boolean isIndicator() {
        return indicator;
    }

    public void setIndicator(boolean indicator) {
        this.indicator = indicator;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
