package com.dli.entities;


import java.util.Date;

public class Answer {

    private int answer_id;
    private int question_id;
    private int answer_num;
    private String answer_option;
    private String answer_content;
    private Boolean is_right;
    private boolean indicator;
    private Date created_at;
    private Date updated_at;


    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_num() {
        return answer_num;
    }

    public void setAnswer_num(int answer_num) {
        this.answer_num = answer_num;
    }

    public String getAnswer_option() {
        return answer_option;
    }

    public void setAnswer_option(String answer_option) {
        this.answer_option = answer_option;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public Boolean getIs_right() {
        return is_right;
    }

    public void setIs_right(Boolean is_right) {
        this.is_right = is_right;
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

        Answer answer = (Answer) o;

        return answer_id == answer.answer_id;
    }

    @Override
    public int hashCode() {
        return answer_id;
    }
}
