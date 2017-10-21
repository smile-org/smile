package com.dli.services;


import com.dli.entities.Demo;
import com.dli.entities.User;

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

}
