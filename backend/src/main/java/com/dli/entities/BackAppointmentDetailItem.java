package com.dli.entities;


import java.util.Date;

public class BackAppointmentDetailItem {
    private int itemId;
    private String itemTitle;
    private String sponsorName;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
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

    private Date sponsorDate;



}
