package com.dli.services.impl;


import com.dli.entities.*;
import com.dli.repositories.AgencyRepo;
import com.dli.repositories.CompanyRepo;
import com.dli.services.AgencyService;
import com.dli.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyRepo agencyRepo;


    @Override
    public void adminAddAgency(Agency a) {
        agencyRepo.adminAddAgency(a);
    }

    @Override
    public Agency adminGetAgencyByID(int agencyid) {
        return agencyRepo.adminGetAgencyByID(agencyid);
    }

    @Override
    public void adminUpdateAgency(Agency a) {
        agencyRepo.adminUpdateAgency(a);
    }

    @Override
    public List<Agency> adminGetAgencyList(Agency a) {
        return agencyRepo.adminGetAgencyList(a);
    }

    @Override
    public int adminGetAgencyListCount(Agency a) {
        return agencyRepo.adminGetAgencyListCount(a);
    }

    @Override
    public void adminUpdateAgencyStatusByID(int status, int agencyid) {
        agencyRepo.adminUpdateAgencyStatusByID(status, agencyid);
    }

    @Override
    public List<adminNameCountStatistics> adminGetAgencyStatistics() {
        return   agencyRepo.adminGetAgencyStatistics();
    }
}
