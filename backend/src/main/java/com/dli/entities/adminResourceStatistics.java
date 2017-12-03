package com.dli.entities;

public class adminResourceStatistics {

   private  int  course_count;
    private  int exam_count;
    private  int  enrollment_count;
    private  int appointment_count;

    public int getCourse_count() {
        return course_count;
    }

    public void setCourse_count(int course_count) {
        this.course_count = course_count;
    }

    public int getExam_count() {
        return exam_count;
    }

    public void setExam_count(int exam_count) {
        this.exam_count = exam_count;
    }

    public int getEnrollment_count() {
        return enrollment_count;
    }

    public void setEnrollment_count(int enrollment_count) {
        this.enrollment_count = enrollment_count;
    }

    public int getAppointment_count() {
        return appointment_count;
    }

    public void setAppointment_count(int appointment_count) {
        this.appointment_count = appointment_count;
    }
}
