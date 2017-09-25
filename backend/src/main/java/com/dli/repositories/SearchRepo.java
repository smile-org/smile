package com.dli.repositories;


import com.dli.entities.Demo;
import com.dli.entities.SearchHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchRepo {

     void addSearchHistory(@Param("userid") int  userid, @Param("keyword") String keyword , @Param("type") String   type );

     List<SearchHistory>  getsearchHistoryList( @Param("userid") int userid,@Param("type")   String type  );

     List<String>   getRecommanedKeyWords( @Param("type") String type );

     void  deleteSearchHistory(  @Param("type") String  type,    @Param("userid")  int userid);



}
