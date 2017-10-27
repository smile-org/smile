package com.dli.entities;


import java.util.Date;

public class EnrollmentContent {

    private int content_id;
    private int sequnce_num;
    private String sequnce_title;
    private String content;
    private int enrollment_id;
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

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
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





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnrollmentContent that = (EnrollmentContent) o;

        return content_id == that.content_id;
    }

    @Override
    public int hashCode() {
        return content_id;
    }



}
