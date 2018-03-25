package com.dli.controllers;

import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.AppointmentService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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




    @RequestMapping(value = "/cancelFollow", method = RequestMethod.GET)
    public Map cancelFollow(int appointmentId, int itemId, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            appointmentService.cancelFollow(appointmentId, itemId, user.getUser_id(), "follower");
            result.put(Constant.status, 1);
            result.put(Constant.result, "点赞取消成功");


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
    public Map backGetAppointmentList(@RequestHeader Map header, @RequestParam String title, @RequestParam String start,  @RequestParam   String end, @RequestParam int skip, @RequestParam int take) {
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
            /* Date newSponsorDate = null;
            if (sponsorDate != null && !sponsorDate.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                newSponsorDate = sdf.parse(sponsorDate);
            } */

            Date startCondition =null;
            Date endCondition =null;

            if(  !Helper.isNullOrEmpty( start) )
                startCondition =Helper.dateParse(  start);
            if (  !Helper.isNullOrEmpty( end) )
                endCondition =Helper.addOneDay(end);

            int total = appointmentService.getBackAppointmentCount(companyId, title, startCondition,endCondition);

            List<BackAppointment> backAppointments = appointmentService.getBackAppointmentList(companyId, title, startCondition ,endCondition, skip, take);

            result.put(Constant.status, 1);
            result.put("total", total);
            result.put(Constant.result, backAppointments);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/backAppointment", method = RequestMethod.GET)
    public Map getBackAppointment(@RequestHeader Map header, @RequestParam int appointmentId) {
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
    public Map getBackAppointmentFollowers(@RequestHeader Map header, @RequestParam int appointmentId) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            List<BackAppointmentFollower> folowers = appointmentService.getBackAppointmentFollowers(appointmentId);

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
    public Map closeAppointment(@RequestHeader Map header, @RequestParam int appointmentId) {
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

    @Value("${fileroot}")
    private String fileroot;

    @Value("${exportfolder}")
    private  String exportfolder;

    @RequestMapping(value = "/exportAppointment", method = RequestMethod.GET)
    public Map exportAppointment( @RequestParam String title,
                                 @RequestParam String start,@RequestParam   String end  ,@RequestHeader Map header) {

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

            /*Date newSponsorDate = null;
            if (sponsorDate != null && !sponsorDate.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                newSponsorDate = sdf.parse(sponsorDate);
            }*/



            Date startCondition =null;
            Date endCondition =null;

            if(  !Helper.isNullOrEmpty( start) )
                startCondition =Helper.dateParse(  start);
            if (  !Helper.isNullOrEmpty( end) )
                endCondition =Helper.addOneDay(end);

            List<BackAppointment> appointmentList =
                    appointmentService.getBackAppointmentList(companyId, title, startCondition,endCondition, 0, Constant.takeMax);

            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("约课主题");
            rowNameList.add("发起者");
            rowNameList.add("发起时间");
            rowNameList.add("关键词");

            List<Object[]> dataList = new ArrayList<>();
            for (BackAppointment appointment : appointmentList) {
                Object[] dataArray = new Object[4];

                dataArray[0] = appointment.getAppointmentTitle();
                dataArray[1] = appointment.getSponsorName();
                dataArray[2] = appointment.getSponsorDate();
                dataArray[3] = appointment.getKey_word();
                dataList.add(dataArray);
            }
            String url = Helper.Export(rowNameList, dataList, "Appointment-", fileroot, exportfolder);

            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;

    }





    @RequestMapping(value = "/adminGetAppointmentList", method = RequestMethod.GET)
    public Map adminGetAppointmentList(@RequestHeader Map header,
                                       @RequestParam String companyname,  @RequestParam String appointmenttitle, @RequestParam int businessid,
                                       @RequestParam String start,  @RequestParam   String end, @RequestParam int skip, @RequestParam int take) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            adminAppointment a =   new  adminAppointment();


            if(  !Helper.isNullOrEmpty( companyname) )
                a.setCompany_name(companyname);
            if(  !Helper.isNullOrEmpty( appointmenttitle) )
                a.setAppointment_title(appointmenttitle);

            if(   businessid  !=0 )
                a.setBusiness_id(businessid);

            if(  !Helper.isNullOrEmpty( start) )
                a.setStart(  Helper.dateParse( start));
            if(  !Helper.isNullOrEmpty( end) )
                a.setEnd(   Helper.addOneDay( end));


            a.setSkip(skip);
            a.setTake(take);




          //  int total = appointmentService.getBackAppointmentCount(companyId, title, startCondition,endCondition);

           // List<BackAppointment> backAppointments = appointmentService.getBackAppointmentList(companyId, title, startCondition ,endCondition, skip, take);


            List<  adminAppointment >   lst =appointmentService.adminGetAppointmentList(a);
            int total =  appointmentService.adminGetAppointmentListCount(a);

            result.put(Constant.status, 1);

            result.put(Constant.result, lst);
            result.put("total", total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }






    @RequestMapping(value = "/adminGetAppointment", method = RequestMethod.GET)
    public Map adminGetAppointment(@RequestHeader Map header,
                                       @RequestParam int appointmentid) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


              adminAppointmentDetail    a   =appointmentService.adminGetAppointment(appointmentid);


            result.put(Constant.status, 1);

            result.put(Constant.result, a);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }

        return result;
    }










    @RequestMapping(value = "/adminExportAppointment", method = RequestMethod.GET)
    public Map adminExportAppointment(@RequestHeader Map header,
                                      @RequestParam String companyname,
                                      @RequestParam String appointmenttitle,
                                      @RequestParam int businessid,
                                      @RequestParam String start,  @RequestParam   String end){

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }
        try {

            adminAppointment a =   new  adminAppointment();


            if(  !Helper.isNullOrEmpty( companyname) )
                a.setCompany_name(companyname);
            if(  !Helper.isNullOrEmpty( appointmenttitle) )
                a.setAppointment_title(appointmenttitle);

            if(   businessid  !=0 )
                a.setBusiness_id(businessid);

            if(  !Helper.isNullOrEmpty( start) )
                a.setStart(  Helper.dateParse( start));
            if(  !Helper.isNullOrEmpty( end) )
                a.setEnd(   Helper.addOneDay( end));


            a.setSkip(0);
            a.setTake(Constant.takeMax);

            List<  adminAppointment >   lst =appointmentService.adminGetAppointmentList(a);

            //导出到服务器
            String sheetName = "sheet1";
            List<String> rowNameList = new ArrayList<>();

            rowNameList.add("约课主题");
            rowNameList.add("企业名称");
            rowNameList.add("主营行业");
            rowNameList.add("发起者");

            rowNameList.add("发起人联系方式");
            rowNameList.add("企业联系人");
            rowNameList.add("发起时间");

            rowNameList.add("关键词");


            List<Object[]> dataList = new ArrayList<>();

            for (adminAppointment  adm : lst) {
                Object[] dataArray = new Object[8];

                dataArray[0] = adm.getAppointment_title();
                dataArray[1] = adm.getCompany_name();
                dataArray[2] = adm.getBusiness_name();
                dataArray[3] = adm.getSponsor_idfull_name();

                dataArray[4] = adm.getSponsor_idcell_phone();
                dataArray[5] = adm.getContact_person();
                dataArray[6] =  Helper.formatDate( adm.getSponsor_date() );

                dataArray[7] =  adm.getKeywords();


                dataList.add(dataArray);
            }

            String url = Helper.Export(rowNameList, dataList, "PlatformAppointment-", fileroot, exportfolder);

            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }







}
