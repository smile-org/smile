package com.dli.services.impl;


import com.dli.entities.SearchHistory;
import com.dli.entities.adminResourceStatistics;
import com.dli.repositories.SearchRepo;
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
        List<String> keywords = searchRepo.getKeywords(userid, type, keyword);
        if (keywords == null || keywords.size() == 0) {
            searchRepo.addSearchHistory(userid, keyword, type);
        }
    }

    @Override
    public List<SearchHistory> getsearchHistoryList(int userid, String type) {
        return searchRepo.getsearchHistoryList(userid, type);
    }

    @Override
    public List<String> getRecommanedKeyWords(String type) {
        return searchRepo.getRecommanedKeyWords(type);
    }

    @Override
    public void deleteSearchHistory(String type, int userid) {
        searchRepo.deleteSearchHistory(type, userid);
    }


}
