package com.dli.entities;


import com.sun.istack.internal.Nullable;

public class ExamRecord {

    private int record_id;
    private int history_id;
    private int question_id;

    private String status;

    private Boolean is_right;

    public Boolean getIs_right() {
        return is_right;
    }

    public void setIs_right(Boolean is_right) {
        this.is_right = is_right;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getHistory_id() {
        return history_id;
    }

    public void setHistory_id(int history_id) {
        this.history_id = history_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
