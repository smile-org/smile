package com.dli.repositories;


import com.dli.entities.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CollectRepo {

    void   addCollection(@Param("userid") int userid, @Param("item_type")String item_type, @Param("item_id")int item_id);

    void   cancelCollection(@Param("userid") int userid, @Param("item_type")String item_type, @Param("item_id")int item_id);
}
