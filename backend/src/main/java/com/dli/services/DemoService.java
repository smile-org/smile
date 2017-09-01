package com.dli.services;


import com.dli.entities.Demo;

import java.util.List;

public interface DemoService {

    List<Demo> getDemoList();

    Demo getDemoById(int id);

    int insertDemo(Demo demo);

    int updateDemo(Demo demo);

    int deleteDemo(int id);

}
