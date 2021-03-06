package com.dli.repositories;


import com.dli.entities.Demo;
import com.dli.entities.Message;
import com.dli.entities.User;
import com.dli.entities.adminMonthCountStatistics;
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

    int   backGetUserListCount(@Param("company_id") int company_id, @Param("full_name")String full_name,@Param("cell_phone")String  cell_phone,
                               @Param("department")String department,@Param("area") String area );

    void  backDisableUserByID( int userid );

    List<User>  backGetCompanyAdminList( @Param("companyid") int companyid  ,@Param("skip") int skip ,@Param("take") int take);

    int  backGetCompanyAdminListCount( int companyid);

    void   backUpdateUserRole(@Param("roleid")int roleid, @Param("userid") int userid);

    List<User>   backGetCompanyEmployeeList ( @Param("fullname") String fullname,  @Param("companyid") int companyid  ,@Param("skip") int skip ,@Param("take") int take);

   int   backGetCompanyEmployeeListCount (  @Param("fullname") String fullname,  @Param("companyid") int companyid);

      void     UpdateUserPic(  @Param("avatar")  String  avatar,   @Param("userid") int userid );

      void   adminAddPlatformUser(User  u);

      void  adminUpdatePlatformUser( User u);

      List<User>  adminGetPlatformUserList( @Param("skip") int skip ,  @Param("take") int take);

    int   adminGetPlatformUserListCount( @Param("skip") int skip ,  @Param("take") int take);

    List<adminMonthCountStatistics>    adminGetEmployeeStatistics();

    int backGetUserCountByCompanyID(  int companyid  );

     void  addMessage(  @Param("cellphone") String  cellphone,  @Param("messageparam") String messageparam   , @Param("templatecode")  String templatecode  ,  @Param("msgtype") String msgtype);



     List<Message>  jobGetMessageList();


     void   jobUpdateMessage( Message   msg  );


     int   getUserByCellphone  (String  cellphone);

}
