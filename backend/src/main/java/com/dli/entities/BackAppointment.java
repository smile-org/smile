package com.dli.entities;

import java.util.Date;

public class BackAppointment {
    private int appointmentId;
    private String appointmentTitle;
    private String sponsorName;
    private Date sponsorDate;
    private   String key_word;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public Date getSponsorDate() {
        return sponsorDate;
    }

    public void setSponsorDate(Date sponsorDate) {
        this.sponsorDate = sponsorDate;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }
}
