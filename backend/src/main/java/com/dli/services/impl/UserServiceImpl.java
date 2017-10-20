package com.dli.services.impl;


import com.dli.entities.Demo;
import com.dli.entities.User;
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
    public void backDisableUserByID(int userid) {
        userRepo.backDisableUserByID(userid);
    }

   /* @Override
    public List<User> backGetUserList(User u) {
        return userRepo.backGetUserList(u);
    }
    */

}
