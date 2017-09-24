package com.dli.services;

import com.dli.entities.Appointment;
import com.dli.entities.AppointmentFollower;
import com.dli.entities.SearchResult;

import java.util.Date;
import java.util.List;


public interface AppointmentService {
    List<Appointment> getAppointmentList(int companyId, int skip, int take);

    Appointment getAppointmentById(int appointmentId, int userId);

    List<AppointmentFollower> getAppointmentFollowList(int appointmentId);

    int follow(int appointmentId, int itemId, int followerId,String followerType);

    int addItem(int appointmentId,String content,int sponsorId, Date sponsorDate);

    int addAppointment(String appointmentTitle,int sponsorId,int companyId,List<String> keywords, List<String> items);

    List<Appointment>  getMyAppointmentList(int userid, int skip, int take);


    List<SearchResult> searchAppointment(int companyid, String keyword, int skip, int take);
}
