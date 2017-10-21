package com.dli.entities;


import java.util.Date;
import java.util.List;

public class BackAppointmentDetail {
    private int appointmentId;
    private String appointmentTitle;
    private String sponsorName;

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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyword) {
        this.keyWord = keyword;
    }

    private Date sponsorDate;

    public List<BackAppointmentDetailItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<BackAppointmentDetailItem> itemList) {
        this.itemList = itemList;
    }

    private String keyWord;

    private List<BackAppointmentDetailItem> itemList;
}
