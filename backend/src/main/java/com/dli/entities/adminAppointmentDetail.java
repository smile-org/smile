package com.dli.entities;

import java.util.Date;
import java.util.List;

public class adminAppointmentDetail {

    private String company_name;
    private String business_name;

    private String appointment_title;
  private  String key_word;

    private String sponsor_idfull_name;
    private String sponsor_idcell_phone;


    private String contact_person;
    private String phone_number;

    public List<BackAppointmentDetailItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<BackAppointmentDetailItem> itemList) {
        this.itemList = itemList;
    }

    private Date sponsor_date;

    private  int follow_count;

    private List<BackAppointmentDetailItem> itemList;


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getAppointment_title() {
        return appointment_title;
    }

    public void setAppointment_title(String appointment_title) {
        this.appointment_title = appointment_title;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    public String getSponsor_idfull_name() {
        return sponsor_idfull_name;
    }

    public void setSponsor_idfull_name(String sponsor_idfull_name) {
        this.sponsor_idfull_name = sponsor_idfull_name;
    }

    public String getSponsor_idcell_phone() {
        return sponsor_idcell_phone;
    }

    public void setSponsor_idcell_phone(String sponsor_idcell_phone) {
        this.sponsor_idcell_phone = sponsor_idcell_phone;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getSponsor_date() {
        return sponsor_date;
    }

    public void setSponsor_date(Date sponsor_date) {
        this.sponsor_date = sponsor_date;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }





}
