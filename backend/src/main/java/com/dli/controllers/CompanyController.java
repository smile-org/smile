package com.dli.controllers;


import com.dli.entities.Company;
import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.helper.FileUtil;
import com.dli.helper.Helper;
import com.dli.services.CompanyService;
import com.dli.services.LogonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

}
