package com.dli.services.impl;


import com.dli.entities.Demo;
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
}
