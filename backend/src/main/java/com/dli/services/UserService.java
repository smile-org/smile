package com.dli.services;


import com.dli.entities.Demo;
import com.dli.entities.User;
import com.dli.entities.adminMonthCountStatistics;

import java.util.List;

public interface UserService {

    void  updatePasswordByUserid( String password,  int userid  );

    void backAddEmployee( User u );

    void   backUpdateUser( User u);

    //List<User>  backGetUserList(User u  );

    List<User>  backGetUserList( int company_id, String full_name,String  cell_phone,
                                String department, String area ,int skip, int take );

    int  backGetUserListCount( int company_id, String full_name,String  cell_phone,
                               String department, String area);

    void  backDisableUserByID( int userid );

    List<User>  backGetCompanyAdminList(  int companyid  , int skip , int take);


    int  backGetCompanyAdminListCount( int companyid);

    void   backUpdateUserRole(int roleid,  int userid);

    List<User>   backGetCompanyEmployeeList ( String fullname, int companyid  ,int skip , int take);

    int   backGetCompanyEmployeeListCount ( String fullname,  int companyid);

    void     UpdateUserPic(  String  avatar,   int userid );

    void   adminAddPlatformUser(User  u);

    void  adminUpdatePlatformUser( User u);

    List<User>  adminGetPlatformUserList(  int skip ,   int take);
  int   adminGetPlatformUserListCount(  int skip ,   int take);

    List<adminMonthCountStatistics>    adminGetEmployeeStatistics();
}
