package com.dli.repositories;


import com.dli.entities.Demo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DemoRepo {

    List<Demo> getDemoList();

    Demo getDemoById(int id);

    int insertDemo(Demo demo);

    int updateDemo(Demo demo);

    int deleteDemo(int id);
}
