package com.dli.services.impl;


import com.dli.entities.Demo;
import com.dli.entities.SearchHistory;
import com.dli.repositories.DemoRepo;
import com.dli.repositories.SearchRepo;
import com.dli.services.DemoService;
import com.dli.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchRepo searchRepo;


    @Override
    public void addSearchHistory(int userid, String keyword, String type) {

        searchRepo.addSearchHistory(userid, keyword, type);
    }

    @Override
    public List<SearchHistory> getsearchHistoryList(int userid, String type) {
        return   searchRepo.getsearchHistoryList(userid, type);
    }

    @Override
    public List<String> getRecommanedKeyWords(String type) {
        return  searchRepo.getRecommanedKeyWords( type);
    }
}
