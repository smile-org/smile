package com.dli.entities;

import java.util.Date;

public class adminAppointment {

    private int appointment_id;
    private String appointment_title;

    private String company_name;

    private int business_id;
    private String business_name;

    private String sponsor_idfull_name;
    private String sponsor_idcell_phone;


    private String contact_person;
    private String phone_number;
    private Date sponsor_date;


    private  Date start;
    private  Date end;


    private  int skip;
    private  int take;



    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getAppointment_title() {
        return appointment_title;
    }

    public void setAppointment_title(String appointment_title) {
        this.appointment_title = appointment_title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
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
}
