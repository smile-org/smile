package com.dli.repositories;


import com.dli.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyRepo {


    void    adminAddAgency( Agency a);
    Agency adminGetAgencyByID(int agencyid);
    void  adminUpdateAgency(Agency a);




    List<Agency> adminGetAgencyList(Agency a);

    int  adminGetAgencyListCount(Agency a);



    void   adminUpdateAgencyStatusByID( @Param("status")  int   status,   @Param("agencyid") int agencyid );


    List<adminNameCountStatistics>  adminGetAgencyStatistics();
}
