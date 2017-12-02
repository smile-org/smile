package com.dli.controllers;


import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.FileUtil;
import com.dli.helper.Helper;
import com.dli.helper.OfficeUtil;
import com.dli.services.CompanyService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/")
public class CompanyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private LogonService logonService;


    @Autowired
    private CompanyService companyService;


    @RequestMapping(value = "/back/GetCompanyByID", method = RequestMethod.GET)
    public Map backGetCompanyByID(@RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            Company c = companyService.backGetCompanyByID(user.getCompany_id());

            result.put(Constant.status, 1);
            result.put(Constant.result, c);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @Value("${defaulthomelogo}")
    private String defaulthomelogo;

    @Value("${defaulthomebanner}")
    private String defaulthomebanner;


    @RequestMapping(value = "/back/SetDefaultCompanyPic", method = RequestMethod.GET)
    public Map backSetDefaultCompanyPic(@RequestParam String pictype, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String url = pictype.equals("logo") ? defaulthomelogo : defaulthomebanner;
            companyService.backSetCompanyPic(url, user.getCompany_id(), pictype);

            result.put(Constant.status, 1);
            result.put(Constant.result, url);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @Value("${fileroot}")
    private String fileroot;

    @Value("${homelogo}")
    private String homelogo;

    @Value("${homebanner}")
    private String homebanner;

    @RequestMapping(value = "/back/UploadCompanyPic", method = RequestMethod.POST)
    public Map backUploadCompanyPic(@RequestParam("pictype") String pictype, @RequestParam("file") MultipartFile file, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            String fileName = pictype + ".png";
            String path = String.format(pictype.equals("logo") ? homelogo : homebanner, user.getCompany_id()).replace(fileName, "");
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);


            companyService.backSetCompanyPic(path + fileName, user.getCompany_id(), pictype);

            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @Value("${homebusinesslicenceprefix}")
    private String homebusinesslicenceprefix;


    @RequestMapping(value = "/admin/UploadCompanyBusinessLicence", method = RequestMethod.POST)
    public Map adminUploadCompanyBusinessLicence(@RequestParam(value = "file", required = true) MultipartFile file, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            //String ext = Helper.getFileNameExtension( file.getOriginalFilename());

            String guid = UUID.randomUUID().toString();
            String fullName = String.format(homebusinesslicenceprefix, 0, guid) + file.getOriginalFilename();
            int lastSlashIndex = fullName.lastIndexOf("/");
            String fileName = fullName.substring(lastSlashIndex + 1);
            String path = fullName.substring(0, lastSlashIndex + 1);
            FileUtil.uploadFile(file.getBytes(), fileroot + path, fileName);

            result.put(Constant.status, 1);
            result.put(Constant.result, path + fileName);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/admin/AddCompany", method = RequestMethod.POST)
    public Map adminAddCompany(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            String company_name = (String) body.get("company_name");
            String contact_person = (String) body.get("contact_person");
            String phone_number = (String) body.get("phone_number");

            int province_id = (int) body.get("province_id");
            int city_id = (int) body.get("city_id");

            int business_id = (int) body.get("business_id");
            int agency_id = (int) body.get("agency_id");
            String address = (String) body.get("address");
            String LincenceUrl = (String) body.get("LincenceUrl");


            adminCompany c = new adminCompany();
            c.setCompany_name(company_name);
            c.setContact_person(contact_person);
            c.setPhone_number(phone_number);
            c.setProvince_id(province_id);
            c.setCity_id(city_id);
            c.setBusiness_id(business_id);
            c.setAgency_id(agency_id);
            c.setAddress(address);
            //c.setLincenceUrl(LincenceUrl);

            companyService.adminAddCompany(c);


            String targetpath = String.format(homebusinesslicenceprefix.replace("%s-", ""), c.getCompany_id());

            // Move file to new directory
            boolean sucess = FileUtil.moveFile(fileroot + LincenceUrl, fileroot + targetpath);


            c.setPic_url(targetpath + LincenceUrl.substring(16));
            c.setPic_type("business_licence");
            companyService.adminAddCompanyPic(c);


            //添加 logo和banner
            c.setPic_url(defaulthomebanner);
            c.setPic_type("banner");
            companyService.adminAddCompanyPic(c);

            c.setPic_url(defaulthomelogo);
            c.setPic_type("logo");
            companyService.adminAddCompanyPic(c);





            result.put(Constant.status, 1);
            result.put(Constant.result, c.getCompany_id());


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/admin/GetCompanyEidtPageInfo", method = RequestMethod.GET)
    public Map adminGetCompanyEidtPageInfo(@RequestParam int companyid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            CompanyEidtPageInfo c = companyService.adminGetCompanyEidtPageInfo(companyid);


            result.put(Constant.status, 1);
            result.put(Constant.result, c);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/admin/UpdateCompanyByID", method = RequestMethod.POST)
    public Map adminUpdateCompanyByID(@RequestBody Map body, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            int company_id = (int) body.get("company_id");
            String company_name = (String) body.get("company_name");
            String contact_person = (String) body.get("contact_person");
            String phone_number = (String) body.get("phone_number");

            int province_id = (int) body.get("province_id");
            int city_id = (int) body.get("city_id");

            int business_id = (int) body.get("business_id");
            int agency_id = (int) body.get("agency_id");
            String address = (String) body.get("address");
            String LincenceUrl = (String) body.get("LincenceUrl");


            adminCompany c = new adminCompany();
            c.setCompany_id(company_id);
            c.setCompany_name(company_name);
            c.setContact_person(contact_person);
            c.setPhone_number(phone_number);
            c.setProvince_id(province_id);
            c.setCity_id(city_id);
            c.setBusiness_id(business_id);
            c.setAgency_id(agency_id);
            c.setAddress(address);
            //c.setLincenceUrl(LincenceUrl);

            companyService.adminUpdateCompany(c);
            companyService.adminUpdateCompanyInfo(c);

            if (LincenceUrl.startsWith("/company-0")) {


                String targetpath = String.format(homebusinesslicenceprefix.replace("%s-", ""), c.getCompany_id());

                // Move file to new directory
                boolean sucess = FileUtil.moveFile(fileroot + LincenceUrl, fileroot + targetpath);


                c.setPic_url(targetpath + LincenceUrl.substring(16));
                c.setPic_type("business_licence");

                companyService.adminUpdateCompanyPic(c);
            }


            result.put(Constant.status, 1);
            result.put(Constant.result, "更新成功");


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/admin/GetCompanyList", method = RequestMethod.GET)
    public Map adminGetCompanyList(String companyname,  int provinceid, int businessid, int agencyid, String start ,   String end  , int skip, int take, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            adminCompany c = new adminCompany();
            if (!Helper.isNullOrEmpty(companyname))
                c.setCompany_name(companyname);

            c.setProvince_id(provinceid);
            c.setBusiness_id(businessid);
            c.setAgency_id(agencyid);


            if (!Helper.isNullOrEmpty(start))
                c.setStart( Helper.dateParse(start));

            if (!Helper.isNullOrEmpty(end)) {
                c.setEnd(   Helper.addOneDay(end)   );
            }


            c.setSkip(skip);
            c.setTake(take);

            List<adminCompany> lst = companyService.adminGetCompanyList(c);
           int total = companyService.adminGetCompanyListCount(c);

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



    @RequestMapping(value = "/admin/UpdateCompanyStatusByID", method = RequestMethod.GET)
    public Map adminUpdateCompanyStatusByID(int status,  int companyid  ,  @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


            companyService.adminUpdateCompanyStatusByID(status, companyid);


            result.put(Constant.status, 1);
            result.put(Constant.result, "状态更新成功");


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }



    @RequestMapping(value = "/admin/GetCityList", method = RequestMethod.GET)
    public Map adminGetCityList(int  provinceid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


            List<City> lst=  companyService.adminGetCityList(provinceid);


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }






    @RequestMapping(value = "/admin/GetIndustryStatistics", method = RequestMethod.GET)
    public Map adminGetIndustryStatistics( @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


           List<adminNameCountStatistics>   lst= companyService.adminGetIndustryStatistics();


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }




    @RequestMapping(value = "/admin/GetCompanyStatistics", method = RequestMethod.GET)
    public Map adminGetCompanyStatistics( @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


            List<adminMonthCountStatistics>   lst= companyService.adminGetCompanyStatistics();


            result.put(Constant.status, 1);
            result.put(Constant.result, lst);


        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }





    @RequestMapping(value = "/admin/GetCompanyAreaStatistics", method = RequestMethod.GET)
    public Map adminGetCompanyAreaStatistics( @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {


            List<adminNameCountStatistics>   lst= companyService.adminGetCompanyAreaStatistics();


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
