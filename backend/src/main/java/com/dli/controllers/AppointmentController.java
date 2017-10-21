package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.services.AppointmentService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.HeaderParam;
import java.text.SimpleDateFormat;
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
    public Map addItem(@RequestBody Map body, @RequestHeader Map header) {
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

            Date date = new Date();

            int itemId = appointmentService.addItem(appointmentId, content, user.getUser_id(), date);
            appointmentService.follow(appointmentId, itemId, user.getUser_id(), "item_sponsor");
            Map map = new HashMap<String, Object>();
            map.put("itemId", itemId);
            map.put("sponsorName", user.getFull_name());
            map.put("sponsorDate", date);
            result.put(Constant.result, map);
            result.put(Constant.status, 1);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }


    @RequestMapping(value = "/addAppointment", method = RequestMethod.POST)
    public Map addAppointment(@RequestBody Map body, @RequestHeader Map header) {
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
            int appointmentId = appointmentService.addAppointment(appointmentTitle, userId, companyId, keywords, items);

            result.put(Constant.status, 1);
            result.put(Constant.result, appointmentId);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/backAppointmentList", method = RequestMethod.GET)
    public Map backGetAppointmentList(@RequestHeader Map header, @RequestParam String title,
                                      @RequestParam String sponsorDate, @RequestParam int skip, @RequestParam int take) {
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
            if (title.equals("")) title = null;
            Date newSponsorDate = null;
            if (sponsorDate != null && !sponsorDate.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                newSponsorDate = sdf.parse(sponsorDate);
            }

            List<BackAppointment> backAppointments = appointmentService.getBackAppointmentList(companyId, title, newSponsorDate, skip, take);

            result.put(Constant.status, 1);
            result.put(Constant.result, backAppointments);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/backAppointment", method = RequestMethod.GET)
    public Map getBackAppointment(@RequestHeader Map header,@RequestParam int appointmentId){
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            BackAppointmentDetail detail = appointmentService.getBackAppointmentDetail(appointmentId);

            result.put(Constant.status, 1);
            result.put(Constant.result, detail);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/backAppointmentFollowers", method = RequestMethod.GET)
    public Map getBackAppointmentFollowers(@RequestHeader Map header,@RequestParam int appointmentId, @RequestParam int skip, @RequestParam int take){
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<BackAppointmentFollower> folowers = appointmentService.getBackAppointmentFollowers(appointmentId,skip,take);

            result.put(Constant.status, 1);
            result.put(Constant.result, folowers);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/closeAppointment", method = RequestMethod.GET)
    public Map closeAppointment(@RequestHeader Map header, @RequestParam int appointmentId){
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            appointmentService.closeAppointment(appointmentId);

            result.put(Constant.status, 1);
            result.put(Constant.result, null);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }


}
