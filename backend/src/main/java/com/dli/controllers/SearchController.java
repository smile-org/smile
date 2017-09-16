package com.dli.controllers;

import com.dli.entities.Course;
import com.dli.entities.SearchHistory;
import com.dli.entities.User;
import com.dli.helper.Constant;
import com.dli.helper.Helper;
import com.dli.services.CourseService;
import com.dli.services.LogonService;
import com.dli.services.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class SearchController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SearchService searchService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LogonService logonService;


    @RequestMapping(value = "/addSearchHistory", method = RequestMethod.GET)
    public Map addSearchHistory(int typeid, String keyword, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();

        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            searchService.addSearchHistory(user.getUser_id(), keyword, Helper.GetItemType(typeid));

            result.put(Constant.status, 1);
            result.put(Constant.result, "关键词添加成功");

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getsearchHistoryList", method = RequestMethod.GET)
    public Map getsearchHistoryList(int typeid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }
        try {
            List<SearchHistory> lst = searchService.getsearchHistoryList(user.getUser_id(), Helper.GetItemType(typeid));

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getRecommanedKeyWords", method = RequestMethod.GET)
    public Map getRecommanedKeyWords(int typeid, @RequestHeader Map header) {
        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {
            List<String> lst = searchService.getRecommanedKeyWords(Helper.GetItemType(typeid));

            result.put(Constant.status, 1);
            result.put(Constant.result, lst);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Map search(String query, int typeid, @RequestHeader Map header) {

        Map<String, Object> result = new HashMap<String, Object>();
        String token = header.get("token").toString();
        User user = logonService.getUserByToken(token);
        if (user == null) {
            result.put(Constant.status, 0);
            result.put(Constant.result, "无效的登录用户");
            return result;
        }

        try {

            switch (typeid) {
                case 1:
                    List<Course> lst = courseService.searchCourse(query);
                    result.put(Constant.status, 1);
                    result.put(Constant.result, lst);
                    break;
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            result.put(Constant.status, 0);
            result.put(Constant.result, ex.getMessage());
        }
        return result;
    }


}
