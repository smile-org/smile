package com.dli.services;


import com.dli.entities.*;

import java.util.List;

public interface CompanyService {

    Company backGetCompanyByID(int companyid);

    void   backSetCompanyPic(String picurl,int companyid,  String pictype);

    void adminAddCompany  ( String avatar ,adminCompany c ,  boolean  isJob) throws  Exception ;
    void   adminAddCompanyPic (adminCompany c);
    //void adminAddConpanyLicence(adminCompany c);

    CompanyEidtPageInfo  adminGetCompanyEidtPageInfo(  int companyid );

    void   adminUpdateCompany(adminCompany c);
    void  adminUpdateCompanyInfo (adminCompany c);

    void   adminUpdateCompanyPic(adminCompany c);
   // void   adminUpdateCompanyLicence (adminCompany c);

    List<adminCompany> adminGetCompanyList(adminCompany  c);

    int  adminGetCompanyListCount(adminCompany  c);

    void   adminUpdateCompanyStatusByID( int   status  , int  companyid );

    List<City>  adminGetCityList(int provinceid);

    List<adminNameCountStatistics>   adminGetIndustryStatistics();

    List<adminMonthCountStatistics>  adminGetCompanyStatistics();



    List<  adminNameCountStatistics>   adminGetCompanyAreaStatistics();

    String   getPicUrl(  int  companyid,   String pictype );

    void   notifycompnayNearExpiration();

    void  jobDisableCompany();

    adminCompany  adminGetCompanyByID( int  companyid );
}
