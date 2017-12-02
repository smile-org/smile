package com.dli.services;


import com.dli.entities.*;

import java.util.List;

public interface AgencyService {

    void    adminAddAgency( Agency a);
    Agency adminGetAgencyByID(int agencyid);

    void  adminUpdateAgency(Agency a);

    List<Agency> adminGetAgencyList(Agency a);

    int  adminGetAgencyListCount(Agency a);

    void   adminUpdateAgencyStatusByID(   int   status,   int agencyid );

    List<adminNameCountStatistics>  adminGetAgencyStatistics();
}
