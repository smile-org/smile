package com.dli.controllers;

import com.dli.entities.Appointment;
import com.dli.entities.AppointmentFollower;
import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.services.AppointmentService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogonService logonService;

    @Autowired
    private AppointmentService appointmentService;


    @RequestMapping(value = "/appointmentList", method = RequestMethod.GET)
    public Map getAppointmentList(int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int companyId = user.getCompany_id();
            List<Appointment> appList = appointmentService.getAppointmentList(companyId, skip, take);
            result.put(Constant.status, 1);
            result.put(Constant.result, appList);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/appointment", method = RequestMethod.GET)
    public Map getAppointment(int appointmentId, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            Appointment app = appointmentService.getAppointmentById(appointmentId, user.getUser_id());
            result.put(Constant.status, 1);
            result.put(Constant.result, app);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/followers", method = RequestMethod.GET)
    public Map getAppointmentFollowerList(int appointmentId, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<AppointmentFollower> list = appointmentService.getAppointmentFollowList(appointmentId);
            result.put(Constant.status, 1);
            result.put(Constant.result, list);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }


    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public Map follow(int appointmentId, int itemId, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int affected = appointmentService.follow(appointmentId, itemId, user.getUser_id(), "follower");
            result.put(Constant.status, 1);
            result.put(Constant.result, affected);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public Map addItem(@RequestBody Map body, @RequestHeader Map header){
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int appointmentId = (int) body.get("appointmentId");
            String content = (String) body.get("content");

            int itemId = appointmentService.addItem(appointmentId, content, user.getUser_id(), new Date());
            appointmentService.follow(appointmentId,itemId,user.getUser_id(),"item_sponsor");
            result.put(Constant.status, 1);
            result.put(Constant.result, itemId);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }


    @RequestMapping(value = "/addAppointment", method = RequestMethod.POST)
    public Map addAppointment(@RequestBody Map body, @RequestHeader Map header){
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int userId = user.getUser_id();
            int companyId = user.getCompany_id();
            String appointmentTitle = (String) body.get("appointmentTitle");
            List<String> keywords = (List<String>) body.get("keywords");
            List<String> items = (List<String>) body.get("items");
            int appointmentId = appointmentService.addAppointment(appointmentTitle,userId,companyId,keywords,items);

            result.put(Constant.status, 1);
            result.put(Constant.result, appointmentId);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }


}
