package com.dli.controllers;

import com.dli.entities.Demo;
import com.dli.services.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CourseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DemoService demoService;



    @RequestMapping(value = "/CategoryList", method = RequestMethod.GET)
    public Map getCategoriesList(int skip, int take) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }

    @RequestMapping(value = "/CourseList", method = RequestMethod.GET)
    public Map getCoursesList(int cateid,int skip, int take) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }

    @RequestMapping(value = "/Course", method = RequestMethod.GET)
    public Map getCourseByID(int courseid) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }

    @RequestMapping(value = "/CourseContentList", method = RequestMethod.GET)
    public Map getCourseContentByCouseID(int courseid) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }

    @RequestMapping(value = "/ClickCourseContent", method = RequestMethod.GET)
    public Map clickCourseContent( int courseid, int contentid) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }

    @RequestMapping(value = "/CourseComment", method = RequestMethod.GET)
    public Map getCourseCommentsByID(  int courseid) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }


    @RequestMapping(value = "/CourseCollection", method = RequestMethod.POST)
    public Map addCourseCollection( int courseid) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }


    @RequestMapping(value = "/CourseComment", method = RequestMethod.POST)
    public Map addCourseComment( @RequestBody Map body) {

        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }
}
