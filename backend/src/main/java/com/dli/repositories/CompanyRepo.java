package com.dli.repositories;


import com.dli.entities.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyRepo {
    Company   backGetCompanyByID(@Param("companyid") int companyid);

    void   backSetCompanyPic(@Param("picurl")String picurl,@Param("companyid")int companyid, @Param("pictype") String pictype);
}
