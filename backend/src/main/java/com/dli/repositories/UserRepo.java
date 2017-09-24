package com.dli.repositories;


import com.dli.entities.Demo;
import com.dli.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRepo {

    User getUserByUserID( int userid);

    void  updatePasswordByUserid(@Param("password") String password,  @Param("userid")int userid  );
}
