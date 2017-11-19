package com.dli.services.impl;

import com.dli.entities.*;
import com.dli.repositories.AppointmentRepo;
import com.dli.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class AppointmentServiceImpl implements AppointmentService {


    @Autowired
    private AppointmentRepo appointmentRepo;

    @Override
    public List<Appointment> getAppointmentList(int companyId, int skip, int take) {
        return appointmentRepo.getAppointmentList(companyId, skip, take);
    }

    @Override
    public Appointment getAppointmentById(int appointmentId, int userId) {
        Appointment app = appointmentRepo.getAppointmentById(appointmentId);
        List<AppointmentItem> items = appointmentRepo.getAppointmentItemList(appointmentId, userId);
        app.setItemList(items);
        return app;
    }

    @Override
    public List<AppointmentFollower> getAppointmentFollowList(int appointmentId) {
        return appointmentRepo.getAppointmentFollowList(appointmentId);
    }

    @Override
    public int follow(int appointmentId, int itemId, int followerId, String followerType) {
        return appointmentRepo.follow(appointmentId, itemId, followerId, followerType);
    }

    @Override
    public void cancelFollow(int appointmentId, int itemId, int followerId, String followerType) {
         appointmentRepo.cancelFollow(appointmentId, itemId, followerId, followerType);
    }

    @Override
    public int addItem(int appointmentId, String content, int sponsorId, Date sponsorDate) {
        appointmentRepo.addItem(appointmentId, content, sponsorId, sponsorDate);
        return appointmentRepo.getMaxItemId();
    }

    @Override
    public int addAppointment(String appointmentTitle, int sponsorId, int companyId, List<String> keywords, List<String> items) {
        appointmentRepo.addAppointment(appointmentTitle, sponsorId, new Date(), companyId);
        int appointmentId = appointmentRepo.getMaxAppointmentId();
        appointmentRepo.follow(appointmentId, null, sponsorId, "appointment_sponsor");
        for (String keyword : keywords) {
            appointmentRepo.addKeyword(appointmentId, keyword);
        }
        for (String item : items) {
            appointmentRepo.addItem(appointmentId, item, sponsorId, new Date());
            int itemId = appointmentRepo.getMaxItemId();
            appointmentRepo.follow(appointmentId, itemId, sponsorId, "item_sponsor");
        }
        return appointmentId;
    }

    @Override
    public List<Appointment> getMyAppointmentList(int userid, int skip, int take) {
        return appointmentRepo.getMyAppointmentList(userid, skip, take);
    }

    @Override
    public List<SearchResult> searchAppointment(int companyid, String keyword, int skip, int take) {
        return appointmentRepo.searchAppointment(companyid, keyword, skip, take);
    }

    @Override
    public List<BackAppointment> getBackAppointmentList(int companyId, String title, Date start,Date end,  int skip, int take) throws ParseException {
       /* Date startDate = null;
        Date endDate = null;


        if (sponsorDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            startDate = sdf1.parse(sdf.format(sponsorDate) + " 00:00:00");
            endDate = sdf1.parse(sdf.format(sponsorDate) + " 23:59:59");
        }
        */
        return appointmentRepo.getBackAppointmentList(companyId, title, start, end, skip, take);
    }

    @Override
    public int getBackAppointmentCount(int companyId, String title, Date start,Date end) throws ParseException {

       /*
        Date startDate = null;
        Date endDate = null;
        if (sponsorDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            startDate = sdf1.parse(sdf.format(sponsorDate) + " 00:00:00");
            endDate = sdf1.parse(sdf.format(sponsorDate) + " 23:59:59");
        }
        */
        return appointmentRepo.getBackAppointmentCount(companyId, title, start, end);
    }

    @Override
    public BackAppointmentDetail getBackAppointmentDetail(int appointmentId) {
        BackAppointmentDetail detail = appointmentRepo.getBackAppointment(appointmentId);
        List<BackAppointmentDetailItem> itemList = appointmentRepo.getBackAppointmentItem(appointmentId);
        detail.setItemList(itemList);
        return detail;
    }

    @Override
    public List<BackAppointmentFollower> getBackAppointmentFollowers(int appointmentId) {
        return appointmentRepo.getBackAppointmentFollowers(appointmentId);
    }

    @Override
    public void closeAppointment(int appointmentId) {
        appointmentRepo.closeAppointment(appointmentId);
    }
}
