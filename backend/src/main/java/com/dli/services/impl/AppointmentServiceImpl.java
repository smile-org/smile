package com.dli.services.impl;

import com.dli.entities.Appointment;
import com.dli.entities.AppointmentFollower;
import com.dli.entities.AppointmentItem;
import com.dli.repositories.AppointmentRepo;
import com.dli.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return   appointmentRepo.getMyAppointmentList(userid,skip,take);
    }
}
