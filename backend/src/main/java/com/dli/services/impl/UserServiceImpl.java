package com.dli.services.impl;


import com.dli.entities.Demo;
import com.dli.entities.User;
import com.dli.entities.adminMonthCountStatistics;
import com.dli.repositories.DemoRepo;
import com.dli.repositories.UserRepo;
import com.dli.services.DemoService;
import com.dli.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void updatePasswordByUserid(String password, int userid) {
        userRepo.updatePasswordByUserid(password,userid);
    }

    @Override
    public void backAddEmployee(User u) {
        userRepo.backAddUser(u);
        userRepo.backAddUserRoleMapping(u.getUser_id(), 1);
    }

    @Override
    public void backUpdateUser(User u) {
        userRepo.backUpdateUser(u);
    }

    @Override
    public List<User> backGetUserList(int company_id, String full_name, String cell_phone, String department, String area ,int skip, int take) {
        return userRepo.backGetUserList(company_id, full_name, cell_phone, department, area ,skip, take);
    }

    @Override
    public int backGetUserListCount(int company_id, String full_name, String cell_phone, String department, String area) {
        return userRepo.backGetUserListCount(company_id, full_name, cell_phone, department, area );
    }

    @Override
    public void backDisableUserByID(int userid) {
        userRepo.backDisableUserByID(userid);
    }

    @Override
    public List<User> backGetCompanyAdminList(int companyid, int skip, int take) {
        return   userRepo.backGetCompanyAdminList(companyid, skip, take);
    }

    @Override
    public int backGetCompanyAdminListCount(int companyid) {
        return  userRepo.backGetCompanyAdminListCount(companyid);
    }

    @Override
    public void backUpdateUserRole(int roleid, int userid) {
        userRepo.backUpdateUserRole(roleid, userid);
    }

    @Override
    public List<User> backGetCompanyEmployeeList( String fullname, int companyid, int skip, int take) {
        return userRepo.backGetCompanyEmployeeList(fullname,companyid, skip, take);
    }

    @Override
    public int backGetCompanyEmployeeListCount( String fullname,  int companyid) {
        return userRepo.backGetCompanyEmployeeListCount(fullname,companyid);
    }

    @Override
    public void UpdateUserPic(String avatar, int userid) {
        userRepo. UpdateUserPic(avatar,userid);
    }

    @Override
    public void adminAddPlatformUser(User u) {
        userRepo.adminAddPlatformUser(u);
        userRepo.backAddUserRoleMapping(u.getUser_id(), 3);

    }

    @Override
    public void adminUpdatePlatformUser(User u) {
        userRepo.adminUpdatePlatformUser(u);
    }

    @Override
    public List<User> adminGetPlatformUserList(int skip, int take) {
        return    userRepo.adminGetPlatformUserList( skip, take);
    }

    @Override
    public int adminGetPlatformUserListCount(int skip, int take) {
        return   userRepo.adminGetPlatformUserListCount(skip,take);
    }

    @Override
    public List<adminMonthCountStatistics> adminGetEmployeeStatistics() {
        return   userRepo.adminGetEmployeeStatistics();
    }

   /* @Override
    public List<User> backGetUserList(User u) {
        return userRepo.backGetUserList(u);
    }
    */

}
