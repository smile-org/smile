package com.dli.services;


import com.dli.entities.*;

import java.util.List;

public interface SearchService {

    void addSearchHistory( int  userid,String keyword ,  String   type );

    List<SearchHistory>  getsearchHistoryList(int userid, String type  );

    List<String>   getRecommanedKeyWords(String type );

    void  deleteSearchHistory( String  type,   int userid);
}
