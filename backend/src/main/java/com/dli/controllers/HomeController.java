package com.dli.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    String abc="123123";

    @RequestMapping("")
    public String index() {
        return "smile backend service is running...";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map add(@RequestBody Map body){
        System.out.print(body);
        return body;
    }
}
