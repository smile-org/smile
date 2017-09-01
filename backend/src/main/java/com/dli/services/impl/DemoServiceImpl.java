package com.dli.services.impl;


import com.dli.entities.Demo;
import com.dli.repositories.DemoRepo;
import com.dli.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepo demoRepo;


    @Override
    public List<Demo> getDemoList() {
        return demoRepo.getDemoList();
    }

    @Override
    public Demo getDemoById(int id) {
        return demoRepo.getDemoById(id);
    }

    @Override
    public int insertDemo(Demo demo) {
        return demoRepo.insertDemo(demo);
    }

    @Override
    public int updateDemo(Demo demo) {
        return demoRepo.updateDemo(demo);
    }

    @Override
    public int deleteDemo(int id) {
        return demoRepo.deleteDemo(id);
    }
}
