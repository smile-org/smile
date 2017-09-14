package com.dli.entities;

import java.util.Date;

public class CourseContent {


    private int content_id;
    private int sequnce_num;
    private String sequnce_title;
    private String content;
    private String orignal_path;
    private String content_link;
    private String content_type;
    private int course_id;
    private boolean indicator;
    private Date created_at;
    private Date updated_at;

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public int getSequnce_num() {
        return sequnce_num;
    }

    public void setSequnce_num(int sequnce_num) {
        this.sequnce_num = sequnce_num;
    }

    public String getSequnce_title() {
        return sequnce_title;
    }

    public void setSequnce_title(String sequnce_title) {
        this.sequnce_title = sequnce_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrignal_path() {
        return orignal_path;
    }

    public void setOrignal_path(String orignal_path) {
        this.orignal_path = orignal_path;
    }

    public String getContent_link() {
        return content_link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
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




}
