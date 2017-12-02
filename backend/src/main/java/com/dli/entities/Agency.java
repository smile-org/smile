package com.dli.entities;

import java.util.Date;

public class Agency {
    private int agency_id;
    private String agency_name;
    private String legal_person;
    private String contact_person;

    private String contact_phone;
    private String agent_area;

    private Date agent_start_date;
    private Date agent_end_date;
    private  int   company_count;

    private  int skip;
    private  int take;

    public int getCompany_count() {
        return company_count;
    }

    public void setCompany_count(int company_count) {
        this.company_count = company_count;
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

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getAgent_area() {
        return agent_area;
    }

    public void setAgent_area(String agent_area) {
        this.agent_area = agent_area;
    }

    public Date getAgent_start_date() {
        return agent_start_date;
    }

    public void setAgent_start_date(Date agent_start_date) {
        this.agent_start_date = agent_start_date;
    }

    public Date getAgent_end_date() {
        return agent_end_date;
    }

    public void setAgent_end_date(Date agent_end_date) {
        this.agent_end_date = agent_end_date;
    }



}
