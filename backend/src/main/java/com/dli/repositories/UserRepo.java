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

    void backAddUser(  User u  );

    void   backAddUserRoleMapping( @Param("userid") int userid  ,@Param("roleid") int roleid );

    void   backUpdateUser( User u);

    //List<User>  backGetUserList(User u  );

    List<User>  backGetUserList(@Param("company_id") int company_id, @Param("full_name")String full_name,@Param("cell_phone")String  cell_phone,
                                @Param("department")String department,@Param("area") String area,  @Param("skip")int skip,@Param("take") int take );

    void  backDisableUserByID( int userid );

    List<User>  backGetCompanyAdminList(  int companyid);
}
