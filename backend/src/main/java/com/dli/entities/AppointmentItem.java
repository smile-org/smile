package com.dli.entities;

import java.util.Date;

public class AppointmentItem {
    private int itemId;
    private String content;
    private int sponsorId;
    private String sponsorName;
    private Date sponsorDate;
    private int followCount;
    private int isFollow;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Date getSponsorDate() {
        return sponsorDate;
    }

    public void setSponsorDate(Date sponsorDate) {
        this.sponsorDate = sponsorDate;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }
}
