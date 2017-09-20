package com.dli.repositories;


import com.dli.entities.Demo;
import com.dli.entities.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepo {

    User getUserByUserID( int userid);
}
