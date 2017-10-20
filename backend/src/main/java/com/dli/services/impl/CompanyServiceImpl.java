package com.dli.services.impl;


import com.dli.entities.Company;
import com.dli.entities.Demo;
import com.dli.repositories.CompanyRepo;
import com.dli.repositories.DemoRepo;
import com.dli.services.CompanyService;
import com.dli.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;


    @Override
    public Company backGetCompanyByID(int companyid) {
        return companyRepo.backGetCompanyByID(companyid);
    }

    @Override
    public void backSetCompanyPic(String picurl, int companyid, String pictype) {
        companyRepo.backSetCompanyPic(picurl, companyid, pictype);
    }
}
