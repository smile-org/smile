package com.dli.services.impl;


import com.dli.entities.*;
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
    public void adminAddCompany(adminCompany c )  throws  Exception {


            companyRepo.adminAddCompany(c);

            companyRepo.adminAddCompanyInfo(c);
            //  companyRepo.adminAddConpanyLicence(c);

            //添加管理员

            User u = new User();
            u.setFull_name(c.getContact_person());
            u.setCell_phone(c.getPhone_number());
            u.setAvatar("/default/header.png");
            u.setCompany_id(c.getCompany_id());

            u.setToken(Helper.GenerateToken(u.getCell_phone()));

            userRepo.backAddUser(u);
            userRepo.backAddUserRoleMapping(u.getUser_id(), 2);



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
}
