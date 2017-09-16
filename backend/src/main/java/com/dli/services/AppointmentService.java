package com.dli.services;

import com.dli.entities.Appointment;
import com.dli.entities.AppointmentFollower;

import java.util.Date;
import java.util.List;


public interface AppointmentService {
    List<Appointment> getAppointmentList(int companyId, int skip, int take);

    Appointment getAppointmentById(int appointmentId, int userId);

    List<AppointmentFollower> getAppointmentFollowList(int appointmentId);

    int follow(int appointmentId, int itemId, int followerId,String followerType);

    int addItem(int appointmentId,String content,int sponsorId, Date sponsorDate);

    int addAppointment(String appointmentTitle,int sponsorId,int companyId,List<String> keywords, List<String> items);
}
