package com.dli.controllers;


import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.FileUtil;
import com.dli.helper.Helper;
import com.dli.services.AgencyService;
import com.dli.services.CompanyService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AgencyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private LogonService logonService;


    @Autowired
    private AgencyService agencyService;





    @RequestMapping(value = "/admin/AddAgency", method = RequestMethod.POST)
    public Map adminAddAgency(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String agency_name = (String) body.get("agency_name");
            String legal_person = (String) body.get("legal_person");
            String contact_person = (String) body.get("contact_person");

            String contact_phone = (String) body.get("contact_phone");
            String agent_area = (String) body.get("agent_area");

            String agent_start_date = (String) body.get("agent_start_date");
            String agent_end_date = (String) body.get("agent_end_date");



            Agency a = new Agency();

            a.setAgency_name(agency_name);
            a.setLegal_person(legal_person);
            a.setContact_person(contact_person);
            a.setContact_phone(contact_phone);
            a.setAgent_area(agent_area);
            a.setAgent_start_date( Helper.dateParse(agent_start_date));
            a.setAgent_end_date( Helper.dateParse(agent_end_date));

            agencyService.adminAddAgency(a);




            result.put(Constant.status, 1);
            result.put(Constant.result, a.getAgency_id());


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/admin/GetAgencyByID", method = RequestMethod.GET)
    public Map adminGetAgencyByID(@RequestParam int agencyid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            Agency a = agencyService.adminGetAgencyByID(agencyid);


            result.put(Constant.status, 1);
            result.put(Constant.result, a);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/admin/Updateagency", method = RequestMethod.POST)
    public Map adminUpdateagency(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            int agency_id = (int) body.get("agency_id");
            String agency_name = (String) body.get("agency_name");
            String legal_person = (String) body.get("legal_person");
            String contact_person = (String) body.get("contact_person");

            String contact_phone = (String) body.get("contact_phone");
            String agent_area = (String) body.get("agent_area");

            String agent_start_date = (String) body.get("agent_start_date");
            String agent_end_date = (String) body.get("agent_end_date");



            Agency a = new Agency();

            a.setAgency_id(agency_id);
            a.setAgency_name(agency_name);
            a.setLegal_person(legal_person);
            a.setContact_person(contact_person);
            a.setContact_phone(contact_phone);
            a.setAgent_area(agent_area);
            a.setAgent_start_date( Helper.dateParse(agent_start_date));
            a.setAgent_end_date( Helper.dateParse(agent_end_date));

            agencyService.adminUpdateAgency(a);




            result.put(Constant.status, 1);
            result.put(Constant.result,  "更新成功");


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/admin/GetAgencyList", method = RequestMethod.GET)
    public Map adminGetAgencyList(String agencyname,  String agentarea  , String end  , int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            Agency a = new Agency();
            if (!Helper.isNullOrEmpty(agencyname))
                a.setAgency_name(agencyname);

            if (!Helper.isNullOrEmpty(agentarea))
                a.setAgent_area(agentarea);

            if (!Helper.isNullOrEmpty(end))
                a.setAgent_end_date( Helper.addOneDay(end)  );

            a.setSkip(skip);
            a.setTake(take);

            List<Agency> lst = agencyService.adminGetAgencyList(a);
            int total = agencyService.adminGetAgencyListCount(a);

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            result.put(Constant.total, total);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/admin/UpdateAgencyStatusByID", method = RequestMethod.GET)
    public Map adminUpdateAgencyStatusByID(int status,  int agencyid  ,  @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


            agencyService.adminUpdateAgencyStatusByID(status, agencyid);


            result.put(Constant.status, 1);
            result.put(Constant.result, "状态更新成功");


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }








    @RequestMapping(value = "/admin/GetAgencyStatistics", method = RequestMethod.GET)
    public Map adminGetAgencyStatistics( @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


            List<adminNameCountStatistics>   lst= agencyService.adminGetAgencyStatistics();


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


}
