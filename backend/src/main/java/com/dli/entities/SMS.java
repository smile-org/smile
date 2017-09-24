package com.dli.entities;


import java.util.Date;

public class SMS {

    private   int    sms_id;
    private  int        user_id;
    private   String   cell_phone;
    private  String         code;
    private    String sms_type;
    private Date created_at;

    public int getSms_id() {
        return sms_id;
    }

    public void setSms_id(int sms_id) {
        this.sms_id = sms_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSms_type() {
        return sms_type;
    }

    public void setSms_type(String sms_type) {
        this.sms_type = sms_type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }





}
