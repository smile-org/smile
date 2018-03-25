package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.repositories.CompanyRepo;
import com.dli.repositories.DemoRepo;
import com.dli.repositories.UserRepo;
import com.dli.services.CompanyService;
import com.dli.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public Company backGetCompanyByID(int companyid) {
        return companyRepo.backGetCompanyByID(companyid);
    }

    @Override
    public void backSetCompanyPic(String picurl, int companyid, String pictype) {
        companyRepo.backSetCompanyPic(picurl, companyid, pictype);
    }

    @Override
    public void adminAddCompany(  String avatar , adminCompany c   , boolean isJob )  throws  Exception {


            companyRepo.adminAddCompany(c);

            companyRepo.adminAddCompanyInfo(c);
            //  companyRepo.adminAddConpanyLicence(c);

            //添加管理员

            User u = new User();
            u.setFull_name(c.getContact_person());
            u.setCell_phone(c.getPhone_number());
            u.setAvatar(avatar);
            u.setCompany_id(c.getCompany_id());

            u.setToken(Helper.GenerateToken(u.getCell_phone()));

        String[] pwd = Helper.generatePwd();
        u.setPassword( pwd[1]);
            userRepo.backAddUser(u);
            userRepo.backAddUserRoleMapping(u.getUser_id(), 2);


            if(  isJob)
            {
                String param = String.format(Constant.newpwd_job_param, pwd[0]);
                userRepo.addMessage(u.getCell_phone(), param, Constant.newpwd_job_templatecode, Constant.newpwd_job);
            }
            else {
                String param = String.format(Constant.newpwd_param, pwd[0]);
                userRepo.addMessage(u.getCell_phone(), param, Constant.newpwd_templatecode, Constant.newpwd);
            }

       //  userRepo.addMessage(u.getCell_phone(),String.format( Constant.newpwdMessage, pwd[0]) , Constant.newpwd);



    }

    @Override
    public void adminAddCompanyPic(adminCompany c) {
        companyRepo.adminAddCompanyPic(c);
    }


    @Override
    public CompanyEidtPageInfo adminGetCompanyEidtPageInfo(int companyid) {
        CompanyEidtPageInfo  entity =new CompanyEidtPageInfo();
        entity.ProvinceList=  companyRepo.adminGetProvinceList();
        entity.BusinessList= companyRepo.adminGetBusinessList();
        entity.AgencyList =companyRepo.adminGetAgencyList();

        if(companyid >0){

            entity.CompanyToBeEdit =companyRepo.adminGetCompanyByID(companyid);
        }
        return  entity;

    }

    @Override
    public void adminUpdateCompany(adminCompany c) {
        companyRepo.adminUpdateCompany(c);
    }

    @Override
    public void adminUpdateCompanyInfo(adminCompany c) {
        companyRepo.adminUpdateCompanyInfo(c);
    }

    @Override
    public void adminUpdateCompanyPic(adminCompany c) {
        companyRepo.adminUpdateCompanyPic(c);
    }

    @Override
    public List<adminCompany> adminGetCompanyList(adminCompany c) {
        return companyRepo.adminGetCompanyList(c);
    }

    @Override
    public int adminGetCompanyListCount(adminCompany c) {
        return companyRepo.adminGetCompanyListCount(c);
    }

    @Override
    public void adminUpdateCompanyStatusByID(int status, int companyid) {
        companyRepo.adminUpdateCompanyStatusByID(status, companyid);
    }

    @Override
    public List<City> adminGetCityList(int provinceid) {
        return companyRepo.adminGetCityList(provinceid);
    }

    @Override
    public List<adminNameCountStatistics> adminGetIndustryStatistics() {
        return   companyRepo.adminGetIndustryStatistics();
    }

    @Override
    public List<adminMonthCountStatistics> adminGetCompanyStatistics() {
        return   companyRepo.adminGetCompanyStatistics();
    }

    @Override
    public List<adminNameCountStatistics> adminGetCompanyAreaStatistics() {
        return   companyRepo.adminGetCompanyAreaStatistics();
    }

    @Override
    public String getPicUrl(int companyid, String pictype) {
        return  companyRepo.getPicUrl(companyid,pictype);
    }

    @Override
    public void notifycompnayNearExpiration() {
        List<adminCompany>  lst =  companyRepo.jobGetCompanyNearExpirationDate();

        for(  adminCompany  c :lst){

            int   count =  companyRepo.jobGetCompany_expiration_notifyCount( c.getExpiration_date(),c.getCompany_id());
            if( count==0){
             //   userRepo.addMessage( c.getPhone_number(),  String.format( Constant.companyexpriationMessage, c.getContact_person(),c.getCompany_name()),
              //          Constant.companyexpriation   );

                userRepo.addMessage( c.getPhone_number(), null,   Constant.companyexpriation_templatecode, Constant.companyexpriation);


                companyRepo.jobAddCompany_expiration_notify(c.getExpiration_date(),c.getCompany_id());


            }

        }
    }

    @Override
    public void jobDisableCompany() {
        companyRepo.jobDisableCompany();
    }

    @Override
    public adminCompany adminGetCompanyByID(int companyid) {
        return  companyRepo.adminGetCompanyByID( companyid );
    }
}
