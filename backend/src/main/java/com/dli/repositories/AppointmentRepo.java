package com.dli.repositories;


import com.dli.entities.Appointment;
import com.dli.entities.AppointmentFollower;
import com.dli.entities.AppointmentItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AppointmentRepo {
    List<Appointment> getAppointmentList(@Param("companyId") int companyId, @Param("skip") int skip, @Param("take") int take);

    Appointment getAppointmentById(int appointmentId);

    List<AppointmentItem> getAppointmentItemList(@Param("appointmentId") int appointmentId, @Param("userId") int userId);

    List<AppointmentFollower> getAppointmentFollowList(int appointmentId);

    int follow(@Param("appointmentId") int appointmentId, @Param("itemId") Integer itemId, @Param("followerId") int followerId, @Param("followerType") String followerType);

    int addItem(@Param("appointmentId") int appointmentId, @Param("content") String content, @Param("sponsorId") int sponsorId, @Param("sponsorDate") Date sponsorDate);

    int getMaxItemId();

    int addAppointment(@Param("appointmentTitle") String appointmentTitle, @Param("sponsorId") int sponsorId, @Param("sponsorDate") Date sponsorDate, @Param("companyId") int companyId);

    int getMaxAppointmentId();

    int addKeyword(@Param("appointmentId") int appointmentId, @Param("word") String word);

    List<Appointment>  getMyAppointmentList(@Param("userid")int userid, @Param("skip")int skip, @Param("take")int take);
}
