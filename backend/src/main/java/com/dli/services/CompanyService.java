package com.dli.services;


import com.dli.entities.Company;
import com.dli.entities.Demo;

import java.util.List;

public interface CompanyService {

    Company backGetCompanyByID(int companyid);

    void   backSetCompanyPic(String picurl,int companyid,  String pictype);
}
