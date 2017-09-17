package com.dli.entities;

import java.util.Date;
import java.util.List;

public class Appointment {

    private int appointmentId;
    private String appointmentTitle;
    private int sponsorId;
    private String sponsorName;
    private Date sponsorDate;
    private String avatar;
    private String keyword;
    private int followerCount;
    private List<AppointmentItem> itemList;

    public List<AppointmentItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<AppointmentItem> itemList) {
        this.itemList = itemList;
    }

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

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public Date getSponsorDate() {
        return sponsorDate;
    }

    public void setSponsorDate(Date sponsorDate) {
        this.sponsorDate = sponsorDate;
    }
}
