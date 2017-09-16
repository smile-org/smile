package com.dli.controllers;

import com.dli.entities.Demo;
import com.dli.entities.SearchHistory;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.DemoService;
import com.dli.services.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/addSearchHistory", method = RequestMethod.GET)
    public Map addSearchHistory( int typeid, String keyword) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            searchService.addSearchHistory( Helper.GetCurrentUser().getUser_id(), keyword  ,  Helper.GetItemType(typeid) );

            result.put(Constant.status, 1);
            result.put(Constant.result, "关键词添加成功");
            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/getsearchHistoryList", method = RequestMethod.GET)
    public Map getsearchHistoryList(int typeid) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            List<SearchHistory>   lst= searchService.getsearchHistoryList( Helper.GetCurrentUser().getUser_id(), Helper.GetItemType(typeid) );

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @RequestMapping(value = "/getRecommanedKeyWords", method = RequestMethod.GET)
    public Map getRecommanedKeyWords(int typeid) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            List<String>   lst= searchService.getRecommanedKeyWords( Helper.GetItemType(typeid) );

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);
            return result;

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }


}
