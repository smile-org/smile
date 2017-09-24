package com.dli.entities;


public class EnrollmentReminderAndTarget {

    private  int period_id;
    private  int target_period_id;

    private  EnrollmentPeriod period;
    private   EnrollmentPeriod targetPeriod;

    public int getPeriod_id() {
        return period_id;
    }

    public void setPeriod_id(int period_id) {
        this.period_id = period_id;
    }

    public int getTarget_period_id() {
        return target_period_id;
    }

    public void setTarget_period_id(int target_period_id) {
        this.target_period_id = target_period_id;
    }

    public EnrollmentPeriod getPeriod() {
        return period;
    }

    public void setPeriod(EnrollmentPeriod period) {
        this.period = period;
    }

    public EnrollmentPeriod getTargetPeriod() {
        return targetPeriod;
    }

    public void setTargetPeriod(EnrollmentPeriod targetPeriod) {
        this.targetPeriod = targetPeriod;
    }
}
