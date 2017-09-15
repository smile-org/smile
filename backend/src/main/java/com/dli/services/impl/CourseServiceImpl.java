package com.dli.services.impl;


import com.dli.entities.Demo;
import com.dli.repositories.CourseRepo;
import com.dli.services.CourseService;
import com.dli.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;



}
