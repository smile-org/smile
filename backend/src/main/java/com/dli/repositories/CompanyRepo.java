package com.dli.repositories;


import com.dli.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyRepo {
    Company   backGetCompanyByID(@Param("companyid") int companyid);

    void   backSetCompanyPic(@Param("picurl")String picurl,@Param("companyid")int companyid, @Param("pictype") String pictype);

    void adminAddCompany(adminCompany  c);

    void  adminAddCompanyInfo(adminCompany  c);

    void   adminAddCompanyPic (adminCompany c);





    List<Province>   adminGetProvinceList();
    List<City>  adminGetCityList( int provinceid);
       List<Business> adminGetBusinessList();
       List<Agency>   adminGetAgencyList();

    adminCompany  adminGetCompanyByID( int  companyid );


    void   adminUpdateCompany(adminCompany c);
    void  adminUpdateCompanyInfo (adminCompany c);
    void   adminUpdateCompanyPic(adminCompany c);

    List<adminCompany> adminGetCompanyList(adminCompany  c);

    int  adminGetCompanyListCount(adminCompany  c);


    void   adminUpdateCompanyStatusByID(@Param("status") int   status  , @Param("companyid")int  companyid );


    List<adminNameCountStatistics>   adminGetIndustryStatistics();


    List<adminMonthCountStatistics>  adminGetCompanyStatistics();


    List<  adminNameCountStatistics>   adminGetCompanyAreaStatistics();

   String   getPicUrl( @Param("companyid")  int  companyid,  @Param("pictype") String pictype );


}
